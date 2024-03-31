(ns ui.components.quick-buttons
  (:require [cljs.core.async.interop :as asy :refer [<p!]]
            [reagent.core :as r :refer [atom]]
            [cljs.core.async :as async :refer [<! >! go chan put! take! timeout]]
            [ui.extract-data.chat :as ed :refer [data-for-nodes data-for-nodes get-all-images-for-node]]
            [ui.components.chat :refer [chat-context]]
            [ui.components.chin :refer [chin]]
            [ui.components.graph-overview-ai :refer [filtered-pages-button]]
            [ui.utils :refer [button-popover model-mappings get-safety-settings update-block-string-for-block-with-child settings-button-popover image-to-text-for p get-child-of-child-with-str title->uid q block-with-str-on-page? call-llm-api update-block-string uid->title log get-child-with-str get-child-of-child-with-str-on-page get-open-page-uid get-block-parent-with-order get-focused-block create-struct gen-new-uid default-chat-struct get-todays-uid]]
            ["@blueprintjs/core" :as bp :refer [ControlGroup Checkbox Tooltip HTMLSelect Button ButtonGroup Card Slider Divider Menu MenuItem Popover MenuDivider]]))




(defn extract-context-children-data-as-str [context]
  (let [res (r/atom "")
        children (sort-by :order (:children @context))]
    (doseq [child children]
      (let [child-str (:string child)]
        ;(println "res " child-str)
        (swap! res str  (str child-str "\n"))))
    res))

(comment
  (get-child-of-child-with-str-on-page "LLM chat settings" "Quick action buttons" "Summarise this page" "Context"))


(defn button-with-settings [button-name]
  (let [block-uid (block-with-str-on-page? (title->uid "LLM chat settings") "Quick action buttons")
        get-linked-refs? (r/atom (if (= "true" (get-child-of-child-with-str block-uid "Settings" "Get linked refs"))
                                   true
                                   false))
        active? (r/atom false)
        default-max-tokens (r/atom (js/parseInt (get-child-of-child-with-str block-uid "Settings" "Max tokens")))
        default-temp (r/atom (js/parseFloat (get-child-of-child-with-str block-uid "Settings" "Temperature")))
        default-model (r/atom (get-child-of-child-with-str block-uid "Settings" "Model"))
        context (r/atom (get-child-of-child-with-str-on-page "LLM chat settings" "Quick action buttons" button-name "Context"))]

    (fn [_]
      #_(println "--" (get-child-of-child-with-str-on-page "llm chat" "Quick action buttons" button-name "Context"))
      [:> ButtonGroup
       {:class-name "button-with-settings"
        :style {:overflow "hidden"
                :display "flex"
                :flex-direction "row"
                :justify-content "space-between"
                :align-items "center"
                :flex "1 1 1"}
        :minimal true}
       [:div {:style {:flex "1 1 1"}}
        [settings-button-popover
         [:> Card {:elevation 3
                   :style {:flex "1"
                           :margin "0"
                           :display "flex"
                           :flex-direction "column"
                           :border "2px solid rgba(0, 0, 0, 0.2)"
                           :border-radius "8px"}}
          [:div.summary-component
            {:style {:box-shadow "rgb(175 104 230) 0px 0px 5px 0px"}}
            [:div.chat-input-container
             {:style {:display "flex"
                      :flex-direction "row"
                      :background-color "#f6cbfe3d"
                      :border "1px"}}
             [chat-context context #()]]
            [chin default-model default-max-tokens default-temp get-linked-refs? active? block-uid]]]]]
       [:div {:style {:flex "1 1 1"}}
         [:> Button {:minimal true
                     :small true
                     :loading @active?
                     :on-click (fn [e]
                                 (p "*Summarise this page* :button clicked")
                                 (when (not @active?)
                                   (reset! active? true)
                                  (go
                                   (let [pre               "*Summarise this page* :"
                                         current-page-uid    (<p! (get-open-page-uid))
                                         title               (uid->title current-page-uid)
                                         block-data          (when (nil? title)
                                                               (str
                                                                 "```"
                                                                 (clojure.string/join "\n -----" (data-for-nodes
                                                                                                   [current-page-uid]
                                                                                                   @get-linked-refs?
                                                                                                   true))
                                                                 "```"))
                                         already-summarised? (block-with-str-on-page? current-page-uid "AI summary")
                                         parent-block-uid    (gen-new-uid)
                                         res-block-uid       (gen-new-uid)
                                         struct              (if (nil? already-summarised?)
                                                               {:s "AI summary"
                                                                :u parent-block-uid
                                                                :c [{:s (str "By: " @default-model)
                                                                     :c [{:s ""
                                                                          :u res-block-uid}]}]}
                                                               {:s (str "By: " @default-model)
                                                                :c [{:s ""
                                                                     :u res-block-uid}]})
                                         top-parent          (if (nil? already-summarised?)
                                                               current-page-uid
                                                               already-summarised?)
                                         context             (extract-context-children-data-as-str
                                                               (r/atom (get-child-of-child-with-str-on-page
                                                                         "LLM chat settings" "Quick action buttons" button-name "Context")))
                                         page-data           (when-not (nil? title) (data-for-nodes [(str title)] @get-linked-refs?))
                                         send-data           (if (nil? title)
                                                                 (str @context "\n" block-data)
                                                                 (str @context "\n" page-data))
                                         settings            (merge
                                                               {:model       (get model-mappings @default-model)
                                                                :max-tokens  @default-max-tokens
                                                                :temperature @default-temp}
                                                               (when (= "gemini" @default-model)
                                                                 {:safety-settings (get-safety-settings block-uid)}))
                                         messages            [{:role "user"
                                                               :content send-data}]]
                                     (do
                                       (create-struct struct top-parent res-block-uid false
                                         (p (str pre "Created a new `AI summary` block with uid: " res-block-uid " and parent uid: " parent-block-uid "and with context: ")))

                                       (<p! (js/Promise.
                                              (fn [_]
                                                (p (str pre "Calling openai api, with settings : " settings))
                                                (p (str pre "and messages : " messages))
                                                (p (str pre "Now sending message and wait for response ....."))
                                                (call-llm-api
                                                  {:messages messages
                                                   :settings settings
                                                   :callback (fn [response]
                                                               (p (str pre "openai api response received: " response))
                                                               (let [res-str (-> response
                                                                               :body)]
                                                                 (update-block-string
                                                                   res-block-uid
                                                                   (str res-str)
                                                                   (js/setTimeout
                                                                     (fn []
                                                                       (p (str pre "Updated block " res-block-uid " with response from openai api"))
                                                                       (reset! active? false))
                                                                     500))))})))))))))}

                    "Summarise this page"]]])))

(defn generate-description-for-images-without-one [block-uid description-option]
  [:div.img-desc
   [button-popover
    (str "Generate description for: " @description-option)
    [:div
     [:> Menu.Item
      {:text "All images"
       :on-click (fn [e]
                   #_(js/console.log "clicked menu item" e)
                   (update-block-string-for-block-with-child block-uid "Settings" "Generate description for:" "All images")
                   (reset! description-option "All images"))}]
     [:> Divider]
     [:> Menu.Item
      {:text "Images without description"
       :on-click (fn [e]
                   #_(js/console.log "clicked menu item" e)
                   (update-block-string-for-block-with-child block-uid "Settings" "Generate description for:" "Images without description")
                   (reset! description-option "Images without description"))}]]]])


(defn text-to-image-button  []
  (let [total-images-count  (r/atom 1)
        loading?            (r/atom false)
        block-uid           (block-with-str-on-page? (title->uid "LLM chat settings") "Quick action buttons")
        img-block-uid       (:uid (get-child-with-str block-uid "Image prompt"))
        image-prompt        (r/atom (get-child-of-child-with-str-on-page "LLM chat settings" "Quick action buttons" "Image prompt" "Default prompt"))
        default-max-tokens  (r/atom (js/parseInt (get-child-of-child-with-str img-block-uid "Settings" "Max tokens")))
        description-for     (r/atom  (get-child-of-child-with-str img-block-uid "Settings" "Generate description for:"))]

    (fn []
      [:> ButtonGroup
        {:class-name "image-button-with-settings"
         :style {:overflow "hidden"
                 :display "flex"
                 :flex-direction "row"
                 :justify-content "space-between"
                 :align-items "center"
                 :flex "1 1 1"}
         :minimal true}
        [:div {:style {:flex "1 1 1"}}
          [settings-button-popover
           [:> Card {:elevation 3
                     :style {:flex "1"
                             :margin "0"
                             :display "flex"
                             :flex-direction "column"
                             :border "2px solid rgba(0, 0, 0, 0.2)"
                             :border-radius "8px"}}
            [:div.summary-component
             {:style {:box-shadow "rgb(175 104 230) 0px 0px 5px 0px"}}
             [:div.chat-input-container
              {:style {:display "flex"
                       :flex-direction "row"
                       :background-color "#f6cbfe3d"
                       :border "1px"}}
              [chat-context image-prompt #()]]
             [chin nil default-max-tokens nil nil nil img-block-uid nil (generate-description-for-images-without-one img-block-uid description-for)]]]]]
       [:div {:style {:flex "1 1 1"}}
        [:> Button {:minimal true
                    :small true
                    :loading @loading?
                    :on-click (fn [e]
                                (go
                                  (let [pre               "*Generate description for each image on page*"
                                        open-page-uid     (<p! (get-open-page-uid))
                                        page-title        (uid->title open-page-uid)
                                        all-images        (if (nil? page-title)
                                                            (get-all-images-for-node open-page-uid true @description-for)
                                                            (get-all-images-for-node page-title    false @description-for))
                                        image-prompt-str  (get-child-of-child-with-str block-uid "Image prompt" "Default prompt" true)
                                        image-count       (count all-images)]
                                    (p (str pre "all images on page: " all-images))
                                    (when (> image-count 0)
                                      (reset! total-images-count (count all-images))
                                      (reset! loading? true)
                                      (image-to-text-for all-images total-images-count loading? image-prompt-str default-max-tokens)))))}
         (str "Generate description for: " @description-for)]]])))

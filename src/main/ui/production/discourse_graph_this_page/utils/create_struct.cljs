(ns ui.production.discourse-graph-this-page.utils.create-struct
  (:require [cljs.core.async.interop :as asy :refer [<p!]]
            [ui.common-utils.create-struct :refer [create-struct]]
            [cljs.core.async :as async :refer [<!  >! go chan put! take! timeout]]
            [ui.common-utils.q.block-has-child-with-str? :refer [block-has-child-with-str?]]
            [ui.common-utils.roam.gen-new-uid :refer [gen-new-uid]]
            [ui.common-utils.q.pull-deep-block-data :refer [ui.common-utils.q.pull-deep-block-data]]
            [ui.common-utils.q.get-child-with-str :refer [get-child-with-str]]
            [ui.common-utils.q.title->uid :refer [title->uid]]
            [ui.common-utils.q.pull-deep-block-data :refer [pull-deep-block-data]]
            [ui.common-utils.roam.get-open-page-uid :refer [get-open-page-uid]]
            [ui.common-utils.roam.extract-settings-data :refer [extract-settings-data]]
            [reagent.core :as r]))

(defn create-bare-struct [open-page-uid suggestion-uid loading-messages-uid default-msg]
  (go
    (let [already-suggested? (block-has-child-with-str? open-page-uid  "AI: Discourse node suggestions")
          suggestion-comp-uid (gen-new-uid)
          struct             (if (nil? already-suggested?)
                               {:s "AI: Discourse node suggestions"
                                :op false
                                :c [{:s "{{llm-dg-suggestions}}"
                                     :u suggestion-comp-uid
                                     :op false
                                     :c [{:s "Suggestions"
                                          :u suggestion-uid}
                                         {:s "Type: Ask"}
                                         {:s "Loading messages"
                                          :c [{:s default-msg
                                               :u loading-messages-uid}]}]}]}
                               {:s "{{llm-dg-suggestions}}"
                                :op false
                                :u suggestion-comp-uid
                                :c [{:s "Suggestions"
                                     :u suggestion-uid}
                                    {:s "Type: Ask"}
                                    {:s "Loading messages"
                                     :c [{:s default-msg
                                          :u loading-messages-uid}]}]})

          top-parent          (if (nil? already-suggested?)
                                open-page-uid
                                already-suggested?)]
      (p "4 already-suggested? " already-suggested? open-page-uid)
      (create-struct
        struct
        top-parent
        suggestion-comp-uid
        true))))

(defn create-dg-this-page-struct []
  (go
     (let [block-uid                (block-has-child-with-str? (title->uid "LLM chat settings") "Quick action buttons")
           discourse-graph-page-uid (:uid (get-child-with-str block-uid "Discourse graph this page"))
           data                     (-> discourse-graph-page-uid
                                            (pull-deep-block-data)
                                            extract-settings-data)
           pre-prompt               (r/atom (:pre-prompt  data))
           suggestion-uid           (gen-new-uid)
           open-page-uid            (<p! (get-open-page-uid))
           loading-message-uid      (gen-new-uid)]
         (if (not (some? @pre-prompt)))
           (create-bare-struct open-page-uid suggestion-uid loading-message-uid
                                 "Setting this up: This graph does not have a pre-prompt yet, setting up the prompt now...")
           (do)
             (p "3 pre prompt exists" pre-prompt)
             (create-bare-struct
              open-page-uid
              suggestion-uid
              loading-message-uid
              "Asking llm please wait..."))))
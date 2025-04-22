(ns ui.common-utils.create-struct
  (:require [applied-science.js-interop :as j]
            [cljs.core.async :as async :refer [go]]
            [ui.common-utils.roam.create-new-block-with-id :refer [create-new-block-with-id]]
            [ui.common-utils.roam.create-new-page :refer [create-new-page]]
            [cljs.core.async.interop :as asy :refer [<p!]]))



;; The keys s - string, c - children, u - uid, op - open, o - order
#_(extract-struct
    {:s "AI chats"
     :c [{:s "{{ chat-llm }}"
          :c [{:s "Messages"}
              {:s "Context"}]}]}
    "8yCGreTXI")
(defn create-struct
  ([struct top-parent chat-block-uid open-in-sidebar?]
   (create-struct struct top-parent chat-block-uid open-in-sidebar? #()))
  ([struct top-parent chat-block-uid open-in-sidebar? cb]
   (create-struct struct top-parent chat-block-uid open-in-sidebar? cb 0))
  ([struct top-parent chat-block-uid open-in-sidebar? cb sidebar-pos]
   (let [stack (atom [struct])
         res (atom [top-parent])]
     (go
       (while (not-empty @stack)
         (let [cur                  (first @stack)
               {:keys [t u s o op
                       string
                       title
                       uid
                       order]}      cur
               new-uid              (j/call-in js/window [:roamAlphaAPI :util :generateUID])
               parent               (first @res)
               u                    (or u uid)
               o                    (or o order)
               s                    (or s string)
               t                    (or t title)
               c                    (or (:c cur)
                                        (:children cur))
               args                 {:parent-uid parent
                                     :block-uid  (if (some? u ) u new-uid)
                                     :order      (if (some? o) o "last")
                                     :string     s
                                     :open       (if (some? op) op true)}]
           (swap! stack rest)
           (swap! stack #(vec (concat % (sort-by :order c))))
           (cond
             (some? t) (<p! (create-new-page t (if (some? u) u  new-uid)))
             (some? s) (<p! (create-new-block-with-id args)))
           (swap! res rest)
           (swap! res #(vec (concat % (vec (repeat (count c)
                                                (if (some? u)
                                                  u
                                                  new-uid))))))))))
       (when open-in-sidebar?
         (<p! (-> (j/call-in js/window [:roamAlphaAPI :ui :rightSidebar :addWindow]
                             (clj->js {:window {:type "block"
                                                :block-uid chat-block-uid
                                                :order sidebar-pos}}))
                  (.then (fn []
                             (j/call-in js/window [:roamAlphaAPI :ui :rightSidebar :open]))))))
       cb))
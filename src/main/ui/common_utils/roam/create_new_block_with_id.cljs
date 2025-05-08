(ns ui.common-utils.roam.create-new-block-with-id
  (:require [applied-science.js-interop :as j]))

(defn create-new-block-with-id [{:keys [parent-uid block-uid order string callback open]}]
  (-> (j/call-in js/window [:roamAlphaAPI :data :block :create]
                 (clj->js {:location {:parent-uid parent-uid
                                      :order       order}
                           :block    {:uid    block-uid
                                      :string string
                                      :open   open}}))
      (.then (fn []
               callback))))
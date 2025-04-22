(ns ui.common-utils.roam.create-new-page
  (:require [applied-science.js-interop :as j]
            [ui.common-utils.roam.gen-new-uid :refer [gen-new-uid]]))

(defn create-new-page
  ([title]
   (let [uid (gen-new-uid)]
     (create-new-page title uid)))
  ([title uid]
   (-> (j/call-in js/window [:roamAlphaAPI :data :page :create]
                  (clj->js {:page {:title title
                                   :uid   uid}})))))
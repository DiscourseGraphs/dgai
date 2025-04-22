(ns ui.common-utils.roam.get-open-page-uid
  (:require [applied-science.js-interop :as j]))

(defn get-open-page-uid []
  (j/call-in js/window [:roamAlphaAPI :ui :mainWindow :getOpenPageOrBlockUid]))
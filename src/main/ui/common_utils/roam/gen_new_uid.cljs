(ns ui.common-utils.roam.gen-new-uid
    (:require [applied-science.js-interop :as j]))

(defn gen-new-uid []
  (j/call-in js/window [:roamAlphaAPI :util :generateUID]))
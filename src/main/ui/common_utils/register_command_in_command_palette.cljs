(ns ui.common-utils.register-command-in-command-palette
  (:require [applied-science.js-interop :as j]))


(defn register-command-in-command-palette [label callback]
  (j/call-in js/window [:roamAlphaAPI :ui :commandPalette :addCommand]
             (clj->js
                 {:label label
                  :callback callback})))
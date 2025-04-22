(ns ui.production.discourse-graph-this-page.utils.command-palette
  (:require [reagent.core :as r]
            [ui.common-utils.register-command-in-command-palette :refer [register-command-in-command-palette]]
            [ui.production.discourse-graph-this-page.utils.create-struct :refer [create-dg-this-page-struct]]
            [reagent.dom :as rd]))


(defn register-dgai-open-settings-command []
  (let [dialog-open? (r/atom false)
        callback (fn [e]
                   (let [settings-container (.createElement js/document "div")
                         body (.-body js/document)]
                     (.appendChild body settings-container)
                     (rd/render [discourse-graph-this-page-settings dialog-open?] settings-container)))]
    (register-command-in-command-palette "DGAI: Open Settings" callback)))


(defn register-dgai-dg-this-page-command []
  (register-command-in-command-palette
    "DGAI: Discourse graph this page"
    #(create-dg-struct)))
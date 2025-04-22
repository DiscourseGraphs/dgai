(ns ui.common-utils.q.title->uid
  (:require [ui.common-utils.q.q :refer [q]]))

(defn title->uid [title]
  (ffirst (q '[:find ?uid
               :in $ ?title
               :where
               [?e :node/title ?title]
               [?e :block/uid ?uid]]
             title)))
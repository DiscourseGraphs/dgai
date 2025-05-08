(ns ui.common-utils.q.uid->eid
  (:require [ui.common-utils.q.q :refer [q]]))

(defn uid->eid [uid]
  (ffirst (q '[:find ?eid
               :in $ ?uid
               :where [?eid :block/uid ?uid]]
             uid)))
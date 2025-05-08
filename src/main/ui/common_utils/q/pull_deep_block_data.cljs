(ns ui.common-utils.q.pull-deep-block-data
 (:require [ui.common-utils.q.q :refer [q]]
           [ui.common-utils.q.uid->eid :refer [uid->eid]]))
(defn pull-deep-block-data [uid]
  (q '[:find (pull ?e  [:block/string :block/uid {:block/children ...}])
       :in $ ?e]
     (uid->eid uid)))
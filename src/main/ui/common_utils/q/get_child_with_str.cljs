(ns ui.common-utils.q.get-child-with-str
  (:require [ui.common-utils.q.q :refer [q]]))

(defn get-child-with-str  [block-uid s]
  (ffirst (q '[:find (pull ?c [:block/string :block/uid :block/order {:block/children ...}])
               :in $ ?uid ?s
               :where
               [?e :block/uid ?uid]
               [?e :block/children ?c]
               [?c :block/string ?s]]
             block-uid
             s)))
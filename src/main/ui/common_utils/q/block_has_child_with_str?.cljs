(ns ui.common-utils.q.block-has-child-with-str?)

(defn block-has-child-with-str? [page bstr]
  (ffirst
    (q '[:find ?uid
         :in $ ?today ?bstr
         :where
         [?e :block/uid ?today]
         [?e :block/children ?c]
         [?c :block/string ?bstr]
         [?c :block/uid ?uid]]
       page
       bstr)))

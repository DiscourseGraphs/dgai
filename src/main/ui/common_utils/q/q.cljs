(ns ui.common-utils.q.q)

(defn q
  ([query]
   (let [serialised-query (if (string? query)
                            query
                            (pr-str query))
         roam-api         (.-data (.-roamAlphaAPI js/window))
         q-fn             (.-q roam-api)]
     (-> (.apply q-fn roam-api (array serialised-query))
         (js->clj :keywordize-keys true))))
  ([query & args]
   (let [serialised-query (if (string? query)
                            query
                            (pr-str query))
         roam-api         (.-data (.-roamAlphaAPI js/window))
         q-fn             (.-q roam-api)]
     (-> (.apply q-fn roam-api (apply array (concat [serialised-query] args)))
         (js->clj :keywordize-keys true)))))

(ns ui.miminal-failing-queries
  (:require [ui.utils :refer [q]]))


(let [queries ['[:find
                 (pull ?Claim [:block/string :node/title :block/uid])
                 (pull ?Claim [:block/uid])
                 :where
                 [?MLQ0-wFPZ-Page :block/uid "zNM1R-woR"]
                 [?MLQ0-wFPZ-SPage :node/title "Opposed By"]
                 [?MLQ0-wFPZ-Page :block/uid ?MLQ0-wFPZ-zNM1R-woR-uid]
                 [?zNM1R-woR :block/uid ?MLQ0-wFPZ-zNM1R-woR-uid]
                 [?MLQ0-wFPZ-Block :block/refs ?MLQ0-wFPZ-Page]
                 [?MLQ0-wFPZ-SBlock :block/refs ?MLQ0-wFPZ-SPage]
                 [?MLQ0-wFPZ-SBlock :block/children ?MLQ0-wFPZ-Block]
                 [?MLQ0-wFPZ-PBlock :block/children ?MLQ0-wFPZ-SBlock]
                 [?MLQ0-wFPZ-PBlock :block/refs ?MLQ0-wFPZ-ParentPage]
                 [?MLQ0-wFPZ-ParentPage :node/title ?MLQ0-wFPZ-ParentPage-Title]
                 [?MLQ0-wFPZ-ParentPage :block/uid ?MLQ0-wFPZ-Claim-uid]
                 [?Claim :block/uid ?MLQ0-wFPZ-Claim-uid]
                 [(re-pattern "^\\[\\[CLM\\]\\] - (.*?)$") ?MLQ0-wFPZ-CLM-.*?$-regex]
                 [(re-find ?MLQ0-wFPZ-CLM-.*?$-regex ?MLQ0-wFPZ-ParentPage-Title)]]


               '[:find
                 (pull ?Evidence [:block/string :node/title :block/uid])
                 (pull ?Evidence [:block/uid])
                 :where
                 [?ncQLSQugk-ParentPage :block/uid "tEb8xA-VW"]
                 [?ncQLSQugk-SPage :node/title "Opposed By"]
                 [?ncQLSQugk-ParentPage :block/uid ?ncQLSQugk-tEb8xA-VW-uid]
                 [?tEb8xA-VW :block/uid ?ncQLSQugk-tEb8xA-VW-uid]
                 [?ncQLSQugk-SBlock :block/refs ?ncQLSQugk-SPage]
                 [?ncQLSQugk-SBlock :block/children ?ncQLSQugk-Block]
                 [?ncQLSQugk-Block :block/refs ?ncQLSQugk-Page]
                 [?ncQLSQugk-Page :node/title ?ncQLSQugk-Page-Title]
                 [?ncQLSQugk-Page :block/uid ?ncQLSQugk-Evidence-uid]
                 [?Evidence :block/uid ?ncQLSQugk-Evidence-uid]
                 [?ncQLSQugk-PBlock :block/refs ?ncQLSQugk-ParentPage]
                 [?ncQLSQugk-PBlock :block/children ?ncQLSQugk-SBlock]
                 [(re-pattern "^\\[\\[EVD\\]\\] - (.*?) - (.*?)$") ?ncQLSQugk-EVD-.*?-.*?$-regex]
                 [(re-find ?ncQLSQugk-EVD-.*?-.*?$-regex ?ncQLSQugk-Page-Title)]]



               '[:find
                 (pull ?Claim [:block/string :node/title :block/uid])
                 (pull ?Claim [:block/uid])
                 :where
                 [?4dLjDnA50-DstPage :block/uid "kG9NO_8Gs"]
                 [?4dLjDnA50-RPage :node/title "SupportedBy"]
                 [?4dLjDnA50-DstPage :block/uid ?4dLjDnA50-kG9NO_8Gs-uid]
                 [?kG9NO_8Gs :block/uid ?4dLjDnA50-kG9NO_8Gs-uid]
                 [?4dLjDnA50-DstBlock :block/refs ?4dLjDnA50-DstPage]
                 [?4dLjDnA50-RBlock :block/refs ?4dLjDnA50-RPage]
                 [?4dLjDnA50-DstBlock :block/children ?4dLjDnA50-RBlock]
                 [?4dLjDnA50-RBlock :block/children ?4dLjDnA50-SrcBlock]
                 [?4dLjDnA50-SrcBlock :block/refs ?4dLjDnA50-SrcPage]
                 [?4dLjDnA50-SrcPage :node/title ?4dLjDnA50-SrcPage-Title]
                 [?4dLjDnA50-SrcPage :block/uid ?4dLjDnA50-Claim-uid]
                 [?Claim :block/uid ?4dLjDnA50-Claim-uid]
                 [(re-pattern "^\\[\\[CLM\\]\\] - (.*?)$") ?4dLjDnA50-CLM-.*?$-regex]
                 [(re-find ?4dLjDnA50-CLM-.*?$-regex ?4dLjDnA50-SrcPage-Title)]]


               '[:find
                 (pull ?Claim [:block/string :node/title :block/uid])
                 (pull ?Claim [:block/uid])
                 :where
                 [?vdEZl9db--DstPage :block/uid "e7rtT39GY"]
                 [?vdEZl9db--RPage :node/title "SupportedBy"]
                 [?vdEZl9db--DstPage :block/uid ?vdEZl9db--e7rtT39GY-uid]
                 [?e7rtT39GY :block/uid ?vdEZl9db--e7rtT39GY-uid]
                 [?vdEZl9db--DstBlock :block/refs ?vdEZl9db--DstPage]
                 [?vdEZl9db--RBlock :block/refs ?vdEZl9db--RPage]
                 [?vdEZl9db--DstBlock :block/children ?vdEZl9db--RBlock]
                 [?vdEZl9db--RBlock :block/children ?vdEZl9db--SrcBlock]
                 [?vdEZl9db--SrcBlock :block/refs ?vdEZl9db--SrcPage]
                 [?vdEZl9db--SrcPage :node/title ?vdEZl9db--SrcPage-Title]
                 [?vdEZl9db--SrcPage :block/uid ?vdEZl9db--Claim-uid]
                 [?Claim :block/uid ?vdEZl9db--Claim-uid]
                 [(re-pattern "^\\[\\[CLM\\]\\] - (.*?)$") ?vdEZl9db--CLM-.*?$-regex]
                 [(re-find ?vdEZl9db--CLM-.*?$-regex ?vdEZl9db--SrcPage-Title)]]


               '[:find
                 (pull ?Claim [:block/string :node/title :block/uid])
                 (pull ?Claim [:block/uid])
                 :where
                 [?a_P_NW6r1-SrcPage :block/uid "DYlGACGlS"]
                 [?a_P_NW6r1-RPage :node/title "SupportedBy"]
                 [?a_P_NW6r1-SrcPage :block/uid ?a_P_NW6r1-DYlGACGlS-uid]
                 [?DYlGACGlS :block/uid ?a_P_NW6r1-DYlGACGlS-uid]
                 [?a_P_NW6r1-SrcBlock :block/refs ?a_P_NW6r1-SrcPage]
                 [?a_P_NW6r1-RBlock :block/refs ?a_P_NW6r1-RPage]
                 [?a_P_NW6r1-DstBlock :block/children ?a_P_NW6r1-RBlock]
                 [?a_P_NW6r1-DstBlock :block/refs ?a_P_NW6r1-DstPage]
                 [?a_P_NW6r1-DstPage :node/title ?a_P_NW6r1-DstPage-Title]
                 [?a_P_NW6r1-DstPage :block/uid ?a_P_NW6r1-Claim-uid]
                 [?Claim :block/uid ?a_P_NW6r1-Claim-uid]
                 [?a_P_NW6r1-RBlock :block/children ?a_P_NW6r1-SrcBlock]
                 [(re-pattern "^\\[\\[CLM\\]\\] - (.*?)$") ?a_P_NW6r1-CLM-.*?$-regex]
                 [(re-find ?a_P_NW6r1-CLM-.*?$-regex ?a_P_NW6r1-DstPage-Title)]]

               '[:find
                 (pull ?Claim [:block/string :node/title :block/uid])
                 (pull ?Claim [:block/uid])
                 :where
                 [?I1kprP4n4-SrcPage :block/uid "MWSDGIDjJ"]
                 [?I1kprP4n4-RPage :node/title "SupportedBy"]
                 [?I1kprP4n4-SrcPage :block/uid ?I1kprP4n4-MWSDGIDjJ-uid]
                 [?MWSDGIDjJ :block/uid ?I1kprP4n4-MWSDGIDjJ-uid]
                 [?I1kprP4n4-SrcBlock :block/refs ?I1kprP4n4-SrcPage]
                 [?I1kprP4n4-RBlock :block/refs ?I1kprP4n4-RPage]
                 [?I1kprP4n4-DstBlock :block/children ?I1kprP4n4-RBlock]
                 [?I1kprP4n4-DstBlock :block/refs ?I1kprP4n4-DstPage]
                 [?I1kprP4n4-DstPage :node/title ?I1kprP4n4-DstPage-Title]
                 [?I1kprP4n4-DstPage :block/uid ?I1kprP4n4-Claim-uid]
                 [?Claim :block/uid ?I1kprP4n4-Claim-uid]
                 [?I1kprP4n4-RBlock :block/children ?I1kprP4n4-SrcBlock]
                 [(re-pattern "^\\[\\[CLM\\]\\] - (.*?)$") ?I1kprP4n4-CLM-.*?$-regex]
                 [(re-find ?I1kprP4n4-CLM-.*?$-regex ?I1kprP4n4-DstPage-Title)]]



               '[:find
                 (pull ?Claim [:block/string :node/title :block/uid])
                 (pull ?Claim [:block/uid])
                 :where
                 [?AUfD7MZFg-DstPage :block/uid "574ETGg-c"]
                 [?AUfD7MZFg-RPage :node/title "SupportedBy"]
                 [?AUfD7MZFg-DstPage :block/uid ?AUfD7MZFg-574ETGg-c-uid]
                 [?574ETGg-c :block/uid ?AUfD7MZFg-574ETGg-c-uid]
                 [?AUfD7MZFg-DstBlock :block/refs ?AUfD7MZFg-DstPage]
                 [?AUfD7MZFg-RBlock :block/refs ?AUfD7MZFg-RPage]
                 [?AUfD7MZFg-DstBlock :block/children ?AUfD7MZFg-RBlock]
                 [?AUfD7MZFg-RBlock :block/children ?AUfD7MZFg-SrcBlock]
                 [?AUfD7MZFg-SrcBlock :block/refs ?AUfD7MZFg-SrcPage]
                 [?AUfD7MZFg-SrcPage :node/title ?AUfD7MZFg-SrcPage-Title]
                 [?AUfD7MZFg-SrcPage :block/uid ?AUfD7MZFg-Claim-uid]
                 [?Claim :block/uid ?AUfD7MZFg-Claim-uid]
                 [(re-pattern "^\\[\\[CLM\\]\\] - (.*?)$") ?AUfD7MZFg-CLM-.*?$-regex]
                 [(re-find ?AUfD7MZFg-CLM-.*?$-regex ?AUfD7MZFg-SrcPage-Title)]]

               '[:find
                 (pull ?Evidence [:block/string :node/title :block/uid])
                 (pull ?Evidence [:block/uid])
                 :where
                 [?rbWGR7BCa-ParentPage :block/uid "574ETGg-c"]
                 [?rbWGR7BCa-SPage :node/title "Opposed By"]
                 [?rbWGR7BCa-ParentPage :block/uid ?rbWGR7BCa-574ETGg-c-uid]
                 [?574ETGg-c :block/uid ?rbWGR7BCa-574ETGg-c-uid]
                 [?rbWGR7BCa-SBlock :block/refs ?rbWGR7BCa-SPage]
                 [?rbWGR7BCa-SBlock :block/children ?rbWGR7BCa-Block]
                 [?rbWGR7BCa-Block :block/refs ?rbWGR7BCa-Page]
                 [?rbWGR7BCa-Page :node/title ?rbWGR7BCa-Page-Title]
                 [?rbWGR7BCa-Page :block/uid ?rbWGR7BCa-Evidence-uid]
                 [?Evidence :block/uid ?rbWGR7BCa-Evidence-uid]
                 [?rbWGR7BCa-PBlock :block/refs ?rbWGR7BCa-ParentPage]
                 [?rbWGR7BCa-PBlock :block/children ?rbWGR7BCa-SBlock]
                 [(re-pattern "^\\[\\[EVD\\]\\] - (.*?) - (.*?)$") ?rbWGR7BCa-EVD-.*?-.*?$-regex]
                 [(re-find ?rbWGR7BCa-EVD-.*?-.*?$-regex ?rbWGR7BCa-Page-Title)]]]]

  (doseq [[idx query] (map-indexed vector queries)]
        (-> (q query)
            (.then #(do
                      (println (str "\nResult " query ":----------------------" (prn %)))))

            (.catch #(do
                       (println (str "\nError " query ":---------------------\n" (prn %))))))))




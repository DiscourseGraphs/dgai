(ns ui.common-utils.roam.extract-settings-data)

(defn index-by-string
  "Given a list of blocks, return a map keyed by the block's :string."
  [blocks]
  (reduce (fn [acc b]
            (assoc acc (:string b) b))
          {}
          blocks))

(defn get-first-child-string
  "Given a block that has at least one child with :string,
   return that child's string value."
  [block]
  (-> block :children first :string))

(defn extract-settings-data [data]
  (let [root          (-> data first first)
        top-level-map (index-by-string (:children root))
        settings-block (top-level-map "Settings")
        prompt-block   (top-level-map "Prompt")
        settings-map  (index-by-string (:children settings-block))
        prompt-map  (index-by-string (:children prompt-block))
        ;; Helper to get a setting's value
        get-setting    (fn [key] (get-first-child-string (settings-map key)))
        get-prompt    (fn [key] (get-first-child-string (prompt-map key)))]

    ;; Construct the final data structure:
    {:messages               (top-level-map "Messages")
     :context                (top-level-map "Context")
     :chat                   (top-level-map "Chat")
     :token-count            (js/parseInt (get-setting "Token count"))
     :model                  (get-setting "Model")
     :max-tokens             (js/parseInt (get-setting "Max tokens"))
     :pre-prompt             (get-prompt "Pre-prompt")
     :ref-relevant-notes-prompt(get-prompt "Ref relevant notes prompt")
     :further-instructions   (get-prompt "Further instructions")
     :temperature            (js/parseFloat (get-setting "Temperature"))
     :get-linked-refs?       (= "true" (get-setting "Get linked refs"))
     :active?                (= "true" (get-setting "Active?"))
     :extract-query-pages?   (= "true" (get-setting "Extract query pages"))
     :extract-query-pages-ref? (= "true" (get-setting "Extract query pages ref?"))}))
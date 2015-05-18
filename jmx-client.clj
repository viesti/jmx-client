(ns jmx-client
  (:require [clojure.java.jmx :as jmx]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as string]))

(def cli-options
  [["-p" "--port PORT" "Port number"
    :default 9999
    :parse-fn #(Integer/parseInt %)]
   ["-h" "--host HOST" "Host"
    :default "localhost"]
   ["-f" "--filter FILTER" "ObjectName filter"
    :default "*:*"]
   ["-k" "--key KEY" "Text to prepend to output, e.g. 'hostname.'"
    :default ""]
   ["-H" "--help"]])

(defn sanitize [object-name]
  (-> (.getKeyProperty object-name "name")
      (.replaceAll "\"" "") ;; Kafka 0.8.1.1 wat, '"' in domain name
      (.replaceAll " " "_"))) ;; value and time are separated by
                              ;; space, dont want spaces in the key

(defn print-attributes [filter key]
  (let [current-time (quot (System/currentTimeMillis) 1000)]
    (doseq [object-name (jmx/mbean-names filter)]
      (try
        (doseq [attribute-name (jmx/attribute-names (.getCanonicalName object-name))]
          (let [value (jmx/read object-name attribute-name)]
            (when (number? value)
              (println (str key (sanitize object-name) "." (name attribute-name)
                            " "
                            value
                            " "
                            current-time)))))
        ;; Drop attributes which cannot be read, at least by the
        ;; code above
        (catch Exception e)))))

(defn main [args]
  (let [{:keys [errors options summary] :as opts} (parse-opts args cli-options)]
    (when errors
      (println (string/join \newline errors))
      (System/exit 1))
    (when (:help options)
      (println summary)
      (System/exit 0))
    (jmx/with-connection (select-keys options [:host :port])
      (print-attributes (:filter options) (:key options)))))

(main *command-line-args*)

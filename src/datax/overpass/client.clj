(ns datax.overpass.client
  "Client for the overpass API"
  (:require [clj-http.client :as client]
            [clojure.string :as string]
            [clojure.xml :as xml]
            [datax.utils.filters :as f]))

(def ^:private api-endpoint
  "Enpoint API address"
  "https://overpass-api.de/api/interpreter")

(defn ^:private build-field [k v]
  (format "[%s=\"%s\"]"
          (name k) v))

(defn build-query [request]
  "Build query string from clojure datastructure"
  (when (map? request)
    (let [fields (map (fn [[k v]] (build-field k v)) request)]
      (format "node%s;out;" (string/join "" fields)))))

(defn sanitize-data [body]
  "Clean response body"
  (-> body :content
      (#(f/filter-by-presence % {:tag :node}))))

(defn overpass-request [r]
  "Compute request "
  (when-let [payload (build-query r)]
    (try
      (->
       (client/get api-endpoint {:query-params {"data" payload}
                                 :accept :xml})
       :body .getBytes java.io.ByteArrayInputStream.
       xml/parse
       sanitize-data)
      (catch Exception e {:failure (.getMessage e)}))))

;; (overpass-request {:operator "Orange"})

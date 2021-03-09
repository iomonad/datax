(ns datax.overpass.client
  "Client for the overpass API"
  (:require [clj-http.client :as client]
            [clojure.string :as string]
            [clojure.xml :as xml]))

(def ^:private api-endpoint
  "Enpoint API address"
  "https://overpass-api.de/api/interpreter")

(defn clean-xml [body]
  "Clean body"
  ())

(defn overpass-request [^String query]
  (->
   (client/get api-endpoint {:query-params {"data" query}
                             :accept :xml})
   :body .getBytes java.io.ByteArrayInputStream.
   xml/parse :content))

(overpass-request "query")

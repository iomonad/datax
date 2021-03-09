(ns datax.wikipedia.tables
  "Extracts wikipedia tables to other formats"
  (:require [net.cgrand.enlive-html :as html]
            [clojure.string :as s]))

(defn select-tables [res]
  "Select table class that is commons
   to wikipedia"
  (html/select res [:table.wikitable]))

(defn extract-header [t]
  "Extract header from raw enlive res"
  (->> (html/select t [:tr :th])
       (map html/text)
       (map s/trim-newline)
       vec))

(defn extract-rows [t]
  "Extract rows from raw enlive res"
  (->> (html/select t [:tr])
       (map #(html/select % [:td]))
       (map #(map html/text %))
       (filter not-empty)))

(defn clean-table-convert [t]
  (let [header (extract-header t)
        rows (extract-content t)]
    {:header header, :rows rows}))

(defn transform-tables [tables]
  "Sanitize data to clean clojure map"
  (map clean-table-convert tables))

(defn extract-tables [url]
  "Return a list of tables present on
   the page"
  (-> url java.net.URL.
      html/html-resource
      select-tables
      transform-tables))

(extract-tables "https://fr.wikipedia.org/wiki/Anciens_indicatifs_t%C3%A9l%C3%A9phoniques_%C3%A0_Paris")

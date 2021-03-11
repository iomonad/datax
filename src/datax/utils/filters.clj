(ns datax.utils.filters
  (:require [clojure.set :refer [index]]))

(defn filter-by-presence [coll pres]
  "Filter list of map by value"
  (->
   ((index coll (keys pres)) pres)
   vec))

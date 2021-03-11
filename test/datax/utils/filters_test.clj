(ns datax.utils.filters-test
  (:use [clojure.test])
  (:require [datax.utils.filters :as f]))

(deftest collection-filtering
  (testing "on map values"
    (let [ds [{:pred "meta"}
              {:pred "meta"}
              {:pred "node"}]]
      (is (= (f/filter-by-presence ds {:pred "node"})
             [{:pred "node"}])))))

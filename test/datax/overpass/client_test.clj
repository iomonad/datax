(ns datax.overpass.client-test
  (:use clojure.test)
  (:require [datax.overpass.client :as client]))

(deftest build-query-test
  (testing "query builder 1"
    (let [req {:name "Gielgen"}]
      (is (= (client/build-query req)
             "node[name=\"Gielgen\"];out;")))))

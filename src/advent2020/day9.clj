(ns advent2020.day9
  (:require
   [clojure.string :as str]
   [clojure.math.combinatorics :as comb]
   [advent2020.core :refer [parse-long]]))

(set! *warn-on-reflection* true)

(def input (slurp "inputs/day9"))
(def lines (str/split input #"\n"))
(def nums (map parse-long lines))
(def preamble-length 25)

(def test-input (slurp "inputs/day9_test"))
(def test-lines (str/split test-input #"\n"))
(def test-nums (map parse-long test-lines))
(def test-preamble-length 5)


(defn valid-next-number? [num prev-nums]
  (let [prev-pairs (comb/combinations prev-nums 2)
        prev-sums (map #(apply + %) prev-pairs)]
    (some #{num} prev-sums)))

(defn find-invalid-num [nums preamble-length]
  (loop [preamble (take preamble-length nums)
         current-num (first (drop preamble-length nums))
         rest-of-numbers (rest (drop preamble-length nums))]
    (if (not (valid-next-number? current-num preamble))
      current-num
      (recur
       (conj (vec (drop 1 preamble)) current-num)
       (first rest-of-numbers)
       (rest rest-of-numbers)))))

(defn part1 [nums preamble-length]
  (find-invalid-num nums preamble-length))

#_(time (part1 test-nums test-preamble-length))
#_(time (part1 nums preamble-length))


(defn part2 []
)

#_(time (part2))

#_(require 'advent2020.day9 :reload)

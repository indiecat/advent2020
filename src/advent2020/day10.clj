(ns advent2020.day10
  (:require
   [clojure.string :as str]
   [advent2020.core :refer [parse-int]]))

(set! *warn-on-reflection* true)

(def input (slurp "inputs/day10"))
(def lines (str/split input #"\n"))
(def sorted-ratings (sort (map parse-int lines)))

(def input-test1 (slurp "inputs/day10_test1"))
(def lines-test1 (str/split input-test1 #"\n"))
(def sorted-ratings-test1 (sort (map parse-int lines-test1)))

(def input-test2 (slurp "inputs/day10_test2"))
(def lines-test2 (str/split input-test2 #"\n"))
(def sorted-ratings-test2 (sort (map parse-int lines-test2)))

(defn differences [sorted-ratings]
  (let [max-rating (last sorted-ratings)
        device-rating (+ 3 max-rating)
        outlet-rating 0
        higher-ratings (conj (vec sorted-ratings) device-rating)
        lower-ratings (vec (conj sorted-ratings outlet-rating))]
    (map - higher-ratings lower-ratings)))

(defn part1 [sorted-ratings]
  (let [diffs (differences sorted-ratings)
        one-jolt-count (count (filter #(= 1 %) diffs))
        three-jolt-count (count (filter #(= 3 %) diffs))]
    (* one-jolt-count three-jolt-count)))

#_(time (part1 sorted-ratings))


(defn part2 [sorted-ratings]
  (->> (partition-by identity (differences sorted-ratings))
       (filter #(not= 3 (first %)))
       (map #(drop 1 %))
       (filter #(not (empty? %)))
       (map count)
       (replace {1 2
                 2 4
                 3 7})
       (reduce *)))

#_(time (part2 sorted-ratings))

#_(require 'advent2020.day10 :reload)

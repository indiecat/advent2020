(ns advent2020.day1
  (:require
   [clojure.string :as str]
   [clojure.set :as set]
   [clojure.math.combinatorics :as comb]
   [advent2020.core :refer [parse-long]]))

(set! *warn-on-reflection* true)

(def input (slurp "inputs/day1"))
(def lines (str/split input #"\n"))
(def nums (map parse-long lines))

(defn part1 [nums]
  (let [antinums (map #(- 2020 %) nums)]
    (->> (set/intersection (set nums) (set antinums))
         (apply *))))

#_(time (part1 nums))


(defn part2 [nums]
  (->> (comb/combinations nums 3)
       (filter #(= 2020 (apply + %)))
       (flatten)
       (apply *)))

#_(time (part2 nums))

#_(require 'advent2020.day1 :reload)

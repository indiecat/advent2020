(ns advent2020.day7
  (:require
   [clojure.string :as str]
   [clojure.set :as set]
   [advent2020.core :refer [parse-int]]))

(set! *warn-on-reflection* true)

(def input (slurp "inputs/day7"))
(def lines (str/split input #"\n"))

(defn parse-rule [line]
  (let [[outer, contents] (str/split line #" bags contain ")]
    (as-> contents $
      (str/split $ #"(?<=\d+) | bags?[\.\,]\s?")
      (filter #(not= "no other" %) $)
      (reverse $)
      (apply hash-map $)
      [outer, $])))

(def rules (mapv parse-rule lines))

(defn direct-outer-for [color rules]
  (->> rules
       (filter #(contains? (last %) color))
       (map first)))

(defn all-outer-for [color rules]
  (loop [direct-outer-new (set (direct-outer-for color rules))
         confirmed-valid-outer-previous #{}]
    (if (= (set/difference direct-outer-new confirmed-valid-outer-previous) #{})
      confirmed-valid-outer-previous
      (recur
       (->> (map #(direct-outer-for % rules) direct-outer-new)
            (flatten)
            (set))
       (set/union confirmed-valid-outer-previous direct-outer-new)))))

(defn part1 [rules]
  (count (all-outer-for "shiny gold" rules)))

#_(time (part1 rules))




(defn part2 [])

#_(time (part2))

#_(require 'advent2020.day7 :reload)

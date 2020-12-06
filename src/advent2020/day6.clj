(ns advent2020.day6
  (:require
   [clojure.string :as str]
   [clojure.set :as set]))

(set! *warn-on-reflection* true)

(def input (slurp "inputs/day6"))
(def groups (str/split input #"\n\n"))

(defn group-answers [group]
  (str/replace group "\n" ""))

(defn part1 [groups]
  (->> (map group-answers groups)
       (map set)
       (map count)
       (apply +)))

#_(time (part1 groups))


(defn individual-answers [group]
  (str/split group #"\n"))

(defn common-answers [answers]
  (->> (map set answers)
       (apply set/intersection)))

(defn part2 [groups]
  (->> (map individual-answers groups)
       (map common-answers)
       (map count)
       (apply +)))

#_(time (part2 groups))

#_(require 'advent2020.day6 :reload)

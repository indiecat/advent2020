(ns advent2020.day2
  (:require
   [clojure.string :as str]
   [advent2020.core :refer [parse-int]]))

(set! *warn-on-reflection* true)

(def input (slurp "inputs/day2"))
(def lines (str/split input #"\n"))

(defn parse-pw [line]
  (let [[_ min max letter pw]
        (re-matches #"(\d+)-(\d+) ([a-z]): ([a-z]+)" line)]
    (hash-map :min (parse-int min)
              :max (parse-int max)
              :letter (first letter)
              :pw pw)))

(defn letter-count [s letter]
  (-> (filter #(= letter %) s)
      count))

(defn valid-pw? [pwh]
  (let [actual (letter-count (pwh :pw) (pwh :letter))]
    (and
     (<= (pwh :min) actual)
     (<= actual (pwh :max)))))

(defn part1 [lines]
  (->> lines
       (map parse-pw)
       (filter valid-pw?)
       count))

#_(time (part1 lines))

(defn letter-at [s ix]
  (nth s (- ix 1)))

(defn xor [x y]
  (and
   (or x y)
   (or (not x) (not y))))

(defn valid-pw-2? [pwh]
  (let [letter1 (letter-at (pwh :pw) (pwh :min))
        letter2 (letter-at (pwh :pw) (pwh :max))]
    (xor
     (= letter1 (pwh :letter))
     (= letter2 (pwh :letter)))))

(defn part2 [lines]
  (->> lines
       (map parse-pw)
       (filter valid-pw-2?)
       count))


#_(time (part2 lines))

#_(require 'advent2020.day2 :reload)

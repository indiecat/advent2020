(ns advent2020.day5
  (:require
   [clojure.string :as str]
   [clojure.set :as set]
   [advent2020.core :refer [parse-binary]]))

(set! *warn-on-reflection* true)

(def input (slurp "inputs/day5"))
(def seats (str/split input #"\n"))

(defn row [row-code]
  (-> (replace {\F \0 \B \1} row-code)
      (str/join)
      (parse-binary)))

(defn column [column-code]
  (-> (replace {\L \0 \R \1} column-code)
      (str/join)
      (parse-binary)))

(defn seat-id [seat]
  (as-> seat $
    (re-matches #"([BF]{7})([LR]{3})" $)
    (rest $)
    (+ (* (row (first $)) 8) (column (last $)))))

(defn part1 [seats]
  (->> (map seat-id seats)
       (apply max)))

#_(time (part1 seats))


(defn part2 [seats]
  (let [occupied-seat-ids (set (map seat-id seats))
        min-seat-id (apply min occupied-seat-ids)
        max-seat-id (apply max occupied-seat-ids)
        all-seat-ids (set (range min-seat-id max-seat-id))]
    (first (set/difference all-seat-ids occupied-seat-ids))))

#_(time (part2 seats))

#_(require 'advent2020.day5 :reload)

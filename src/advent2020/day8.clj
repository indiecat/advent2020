(ns advent2020.day8
  (:require
    [clojure.string :as str]
    [clojure.set :as set]
    [clojure.walk :as walk]
    [advent2020.core :refer [parse-int]]))

(set! *warn-on-reflection* true)

(def input (slurp "inputs/day8"))
(def lines (str/split input #"\n"))

(defn parse-instruction [line]
  (let [[op arg] (str/split line #"\s")]
    (zipmap [:op :arg] [op (parse-int arg)])))

(def instructions (mapv parse-instruction lines))

(defn run-instruction [instruction accumulator]
  (case (instruction :op)
    "nop" 1
    "acc" (do (swap! accumulator #(+ (instruction :arg) %)) 1)
    "jmp" (instruction :arg)))

(defn part1 [instructions]
  (let [accumulator (atom 0)]
    (loop [current-ix 0
           used-ixs #{}]
      (if (contains? used-ixs current-ix)
        @accumulator
        (recur
         (+ current-ix
            (run-instruction (nth instructions current-ix) accumulator))
         (conj used-ixs current-ix))))))

#_(time (part1 instructions))


(defn part2 []
)

#_(time (part2))

#_(require 'advent2020.day8 :reload)

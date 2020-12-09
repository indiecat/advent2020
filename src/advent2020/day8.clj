(ns advent2020.day8
  (:require
    [clojure.string :as str]
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


(defn attempt-to-run [instructions]
  (let [accumulator (atom 0)
        termination-ix (count instructions)]
    (loop [current-ix 0
           used-ixs #{}]
      (cond
        (contains? used-ixs current-ix) [:looped used-ixs]
        (= current-ix termination-ix) [:terminated @accumulator]
        :else (recur
               (+ current-ix
                  (run-instruction (nth instructions current-ix) accumulator))
               (conj used-ixs current-ix))))))

(def indexes-to-test (second (attempt-to-run instructions)))

(def indexes-to-alter (filter
                       (fn [ix] (-> (nth instructions ix)
                                    (:op)
                                    (not= "acc")))
                       indexes-to-test))

(defn run-instruction-inverted [instruction accumulator]
  (case (instruction :op)
    "jmp" 1
    "acc" (do (swap! accumulator #(+ (instruction :arg) %)) 1)
    "nop" (instruction :arg)))

(defn attempt-to-run-with-inverted [instructions inverted-ix]
  (let [accumulator (atom 0)
        termination-ix (count instructions)]
    (loop [current-ix 0
           used-ixs #{}]
      (cond
        (contains? used-ixs current-ix) [:looped used-ixs]
        (= current-ix termination-ix) [:terminated @accumulator]
        :else (recur
               (+ current-ix
                  (if (= current-ix inverted-ix)
                    (run-instruction-inverted (nth instructions current-ix) accumulator)
                    (run-instruction (nth instructions current-ix) accumulator)))
               (conj used-ixs current-ix))))))

(defn part2 [instructions]
  (->> (map #(attempt-to-run-with-inverted instructions %) indexes-to-alter)
    (filter #(= (first %) :terminated))
    (first)
    (second)))

#_(time (part2 instructions))

#_(require 'advent2020.day8 :reload)

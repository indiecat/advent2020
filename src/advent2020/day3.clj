(ns advent2020.day3
  (:require
   [clojure.string :as str]))

(set! *warn-on-reflection* true)

(def input (slurp "inputs/day3"))
(def rows (str/split input #"\n"))

(def grid (map cycle rows))

(defn path [grid]
  (for [row-ix (range (count grid))]
    (as-> grid $
      (nth $ row-ix)
      (drop (* row-ix 3) $)
      (first $))))

(defn tree-count [path]
  (->> path
       (filter #(= \# %))
       (count)))

(defn part1 [grid]
  (-> grid
      (path)
      (tree-count)))

#_(time (part1 grid))

(def slopes [{:right 1 :down 1}
             {:right 3 :down 1}
             {:right 5 :down 1}
             {:right 7 :down 1}
             {:right 1 :down 2}])

(defn slope-path [grid slope]
  (for [row-ix (range 0 (count grid) (slope :down))]
    (as-> grid $
      (nth $ row-ix)
      (drop (* (/ row-ix (slope :down)) (slope :right)) $)
      (first $))))


(defn part2 [grid slopes]
  (->> slopes
       (map #(slope-path grid %))
       (map #(tree-count %))
       (apply *)))

#_(time (part2 grid slopes))

#_(require 'advent2020.day3 :reload)

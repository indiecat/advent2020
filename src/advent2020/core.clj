(ns advent2020.core)


(defn parse-long [s]
  (when s
    (Long/parseLong s)))

(defn parse-int [s]
  (when s
    (Integer/parseInt s)))

(defn parse-binary [s]
  (when s
    (Integer/parseInt s 2)))

(defn zip [xs ys]
  (map vector xs ys))

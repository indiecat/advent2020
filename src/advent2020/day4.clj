(ns advent2020.day4
  (:require
   [clojure.string :as str]
   [clojure.set :as set]
   [clojure.walk :as walk]
   [advent2020.core :refer [parse-int]]))

(set! *warn-on-reflection* true)

(def input (slurp "inputs/day4"))
(def records (str/split input #"\n\n"))

(defn passport-fields [record]
  (as-> record $
    (str/split $ #"( |\n)")
    (map #(str/split % #":") $)
    (flatten $)
    (apply hash-map $)
    (walk/keywordize-keys $)))

(def passports (map passport-fields records))

(def mandatory-fields #{:byr :iyr :eyr :hgt :hcl :ecl :pid})

(defn valid-passport? [passport]
  (empty? (set/difference mandatory-fields (set (keys passport)))))

(defn part1 [passports]
  (->> passports
       (filter valid-passport?)
       (count)))

#_(time (part1 passports))


(defn valid-byr? [byr]
  (and
   (re-matches #"\d\d\d\d" byr)
   (<= 1920 (parse-int byr) 2002)))

(defn valid-iyr? [iyr]
  (and
   (re-matches #"\d\d\d\d" iyr)
   (<= 2010 (parse-int iyr) 2020)))

(defn valid-eyr? [eyr]
  (and
   (re-matches #"\d\d\d\d" eyr)
   (<= 2020 (parse-int eyr) 2030)))

(defn valid-hgt? [hgt]
  (when-let [match (re-matches #"(\d\d\d?)(cm|in)" hgt)]
    (or
     (and
      (= (last match) "cm")
      (<= 150 (parse-int (second match)) 193))
     (and
      (= (last match) "in")
      (<= 59 (parse-int (second match)) 76)))))

(defn valid-hcl? [hcl]
  (re-matches #"#[0-9a-f]{6}" hcl))

(defn valid-ecl? [ecl]
  (contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} ecl))

(defn valid-pid? [pid]
  (re-matches #"\d{9}" pid))

(defn valid-passport-2? [passport]
  (and
   (empty? (set/difference mandatory-fields (set (keys passport))))
   (valid-byr? (passport :byr))
   (valid-iyr? (passport :iyr))
   (valid-eyr? (passport :eyr))
   (valid-hgt? (passport :hgt))
   (valid-hcl? (passport :hcl))
   (valid-ecl? (passport :ecl))
   (valid-pid? (passport :pid))))

(defn part2 [passports]
  (->> passports
       (filter valid-passport-2?)
       (count)))

#_(time (part2 passports))

#_(require 'advent2020.day4 :reload)

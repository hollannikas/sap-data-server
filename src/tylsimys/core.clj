(ns tylsimys.core
  (:use [clojure.data.zip.xml])
  (:require [clojure.xml :as xml]
            [clojure.zip :as zip]))

(defn get-parts
  [header]
  (xml-> header
         :CONDP))

(defn get-headers
  [root]
  (xml-> root
         :IDOC
         :CONDH))

(defn parse-xml
  "Read xml from file name"
  [filename]
  (zip/xml-zip
    (xml/parse filename)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println
    (get-headers
      (parse-xml "test/tylsimys/TEST_COND_A.xml"))))

(comment
  (map zip/node
    (get-headers
      (parse-xml "test/tylsimys/TEST_COND_A.xml")))
  (xml-> (parse-xml "test/tylsimys/TEST_COND_A.xml")
         :CONDH))
(ns tylsimys.core
  (:require [clojure.xml :as xml]
            [clojure.zip :as zip]))

(defn zip-map
  [f node]
  (loop [node node]
    (if (identical? (zip/next node) node)
      (zip/root node)
      (if (zip/branch? node)
        (recur (zip/next node))
        (recur (-> node 
                   (zip/edit f)
                   zip/next))))))
  
(defn get-rules
  [root-loc]
  (zip-map println root-loc))

(defn get-root-loc
  "Read xml from file name"
  [filename]
  (zip/xml-zip
    (xml/parse filename)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
    (get-rules
      (get-root-loc "test/tylsimys/TEST_COND_A.xml")))

(comment
         (zip/children (get-root-loc "test/tylsimys/TEST_COND_A.xml")))
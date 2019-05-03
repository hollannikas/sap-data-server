(ns tylsimys.xml
  (:require [clojure.data.zip.xml :as zip-xml]
            [clojure.data.xml :as xml]
            [clojure.zip :as zip]
            [clojure.java.io :as io]
            [tylsimys.db :as db]))

(defn element-content
  [element tag]
  (zip-xml/xml1-> element tag zip-xml/text))

(defn part->map
  [part]
  {:price      (Double/valueOf (element-content part :KBETR))
   :currency   (element-content part :KONWA)
   :start-date (element-content part :DATAB)
   :end-date   (element-content part :DATBI)})

(defn header->map
  [header]
  {:type    (element-content header :KSCHL)
   :store   (element-content header :VKORG)
   :product (bigint (element-content header :MATNR))
   :parts   (mapv part->map (zip-xml/xml-> header :CONDP))})

(defn parse-xml
  [source]
  (let [root (-> source
                 io/reader
                 xml/parse
                 zip/xml-zip)]
    (mapv header->map (zip-xml/xml-> root :IDOC :CONDH))))

(defn save-idoc
  [source]
  ; TODO do we want to return something?
  (doseq [header (parse-xml source)]
    (db/save-header header)))

(comment
  (save-idoc "test/tylsimys/TEST_COND_A.xml")
  (parse-xml "test/tylsimys/TEST_COND_A.xml")
  (-> "test/tylsimys/TEST_COND_A.xml"
      io/reader
      xml/parse
      zip/xml-zip))
  
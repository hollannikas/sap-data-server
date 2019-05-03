(ns tylsimys.db
  (:use [korma.core]
        [korma.db]))

; TODO
; external config
; save parts

(defdb korma-db (postgres {:db       "test"
                           :user     "postgres"
                           :password "postgres"
                           :host     "localhost"
                           :port     "9432"}))

(declare headers)

(defentity headers
           (table :headers)
           (database korma-db)
           (entity-fields
             :product
             :store
             :type))

(defn save-header
  [header]
  (insert headers
          (values
            (select-keys header [:product :store :type]))))
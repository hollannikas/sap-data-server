(ns tylsimys.aws
  (:gen-class)
  (:require [amazonica.aws.sqs :as sqs]
            [amazonica.aws.s3 :as s3]
            [cheshire.core :refer :all]
            [tylsimys.xml :as core]))

; TODO
; use data.xml here!
; poll queue in loop

(def queue {:queue-url              (System/getenv "AWS_Q_URL")
            :wait-time-seconds      6
            :max-number-of-messages 10
            :delete                 true                    ;; deletes any received messages after receipt
            :attribute-names        ["All"]})

(defn s3object->database
  [map]
  (let [bucket (:bucket map)
        key (:key map)]
    (if (clojure.string/ends-with? key ".xml")
      (-> (s3/get-object bucket key)
          :input-stream
          core/save-idoc)
      (println "Not an XML file"))))

(defn record->s3object
  [record]
  (let [object-bucket (get-in record [:s3 :bucket :name])
        object-key (get-in record [:s3 :object :key])]

    (println object-key)
    {:bucket object-bucket :key object-key}))

(defn get-records
  [message]
  (get message :Records))

(defn get-message
  [message]
  "Retrieve the body from an SQS message"
  (parse-string
    (:Message
      (parse-string (:body message) true)) true))

(defn process-messages
  [messages]
  "Process polled SQS messages"
  (mapv s3object->database
        (map record->s3object
             ; TODO Get records out of vector 
             (first
               (map get-records
                    (map get-message (:messages messages)))))))

(comment
  (process-messages
    (sqs/receive-message queue)))
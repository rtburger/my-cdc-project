(ns rtburger.my-cdc-project
  (:import [io.debezium.config Configuration]
           [io.debezium.embedded EmbeddedEngine]
           [io.debezium.embedded EmbeddedEngine$CompletionCallback]
           [io.debezium.embedded EmbeddedEngine$CompletionResult]
           [io.debezium.embedded EmbeddedEngine$ConnectorCallback]
           [io.debezium.embedded EmbeddedEngine$RecordCommitter]
           [io.debezium.embedded EmbeddedEngine$ChangeConsumer])

  (:require [clojure.core.async :refer [>! <! go chan]]
            [com.rpl.proxy-plus :refer [proxy+]]
            [clojure.test :refer :all]))

;;TODO
;; 1/ how will i handle fault tolerance ?
;; 2/ how will i handle scalbility ?
;; 3/ how will i hanlde durability ?
;; 4/ some of the debezium engine code has @deprecated... changing API?


;; Config
(defn create-config [m]
  (Configuration/from m))


;; Define the configuration for the embedded engine and SQL Server connector
(def debezium-config
  {; begin engine properties
   "name" "my-engine" ; "Unique name for this connector instance."
   "connector.class" "io.debezium.connector.sqlserver.SqlServerConnect" ;("The Java class for the connector"
   "offset.storage" "org.apache.kafka.connect.storage.FileOffsetBackingStore" ; The Java class that implements the `OffsetBackingStore `" interface, used to periodically store offsets so that, upon restart, the connector can resume where it last left off.
   "offset.storage.file.filename" "tmpoffsets.dat" ;The file where offsets are to be stored.
   "offset.flush.interval.ms" "0" ; "Interval at which to try committing offsets, given in milliseconds. Defaults to 1 minute (60,000 ms).
   ; begin connector properties
   "database.hostname" "Localhost"
   "database.port" "1433"
   "database.user" "debezium"
   "database.password" ""
   "database.names" "TestDB"
   "database.instance" "myapp"
   "topic.prefix" "sql-source-"
   "schema.history.interval" "io.debezium.storage.file.history.FileSchemaHistory"
   "schema.history.interval.file.filename" "tmp/schemahistory.dat"
   "table.include.list" "inventory"
   "debezium.source.decimal.handling.mode" "string"
   "quarkus.log.consol.json" "false"
   "quarkus.log.level" "DEBUG"})

;; Create the engine with this config
;; TODO what is Clojure idiomatic way for exception handling?

;; for reference https://github.com/debezium/debezium/blob/main/debezium-embedded/src/main/java/io/debezium/embedded/EmbeddedEngine.java

; TODO impl interface CompletionCallback    
(defn create-completion-callback
  ; Handle the completion of the embedded connector engine
  ; LEARNING: The first parameter must be supplied to correspond to the target object ('this' in Java parlance).
  ; https://clojure.github.io/clojure/clojure.core-api.html#clojure.core/reify
  ; reify will  only work with interfaces
  [f]
  (reify EmbeddedEngine$CompletionCallback
    (handle [this success message error]
      (f success message error))))

;  with proxy
; is this right ???
(defn create-completion-callback2 [f]
  (proxy [EmbeddedEngine$CompletionResult] []
    (handle [success message error]
      (f success message error))))

; with rpl proxy+ lib
; is this right ???
(defn create-completion-callback3 [f]
  (proxy+ [] EmbeddedEngine$CompletionResult
          (handle [this success message error]
                  (f success message error))))


; TODO impl interface ConnectorCallback
(defn create-connector-callback
  ; A callback function which informs users about the various stages a connector goes through  during startup
  ; LEARNING: :: is used to auto-resolve a keyword in the current namespace. 
  [f]
  (reify EmbeddedEngine$ConnectorCallback
    ; called after a connector has been successfully started 
    (connectorStarted [this]
      (f ::connector-started))
    ; called after a connector has been successfully stopped
    (connectorStopped [this]
      (f ::connector-stopped))
    ; called after a connector task has been successfully started
    (taskStarted [this]
      (f ::task-started))
    ; called after a connector task has been successfully stopped
    (taskStopped [this]
      (f ::task-stopped))))


; TODO impl inteface RecordCommitter
(defn record-committer [])
; should i wrap RecordCommitter<R>?

; TODO impl interface Offsets
(defn offsets []
  ; should i wrap this offset?
  )

; TODO impl interface ChangeConsumer
(defn change-consumer
  ; handle a catch of records
  [f]
  (reify EmbeddedEngine$ChangeConsumer
    (handleBatch [this records committer]
      (f records committer)
      (.markBatchFinished committer))))






; TODO impl Builder











(defn greet
  "Callable entry point to the application."
  [data]
  (println (str "Hello, " (or (:name data) "World") "!")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (greet {:name (first args)}))




(comment
 ; testing create-completion-callback 
  (defn my-callback [success message error]
    (if error
      (println "error" error)
      (println "no error" error)))


  (let [callback (create-completion-callback3 my-callback)]
    (. callback handle true "hi" nil))


  ; testing create-connector-callback
  (defn my-callback [stage]
    (println "Connector reached stage:" (name stage)))

  (def my-connector-callback (create-connector-callback my-callback))

  (.connectorStarted my-connector-callback)
  (.taskStarted my-connector-callback)
  (.taskStopped my-connector-callback)
  (.connectorStopped my-connector-callback)
  
  ; testing change-consumer


(defn handle-batch [records committer]
  (println "Processing batch of records:" records))

(def my-consumer (change-consumer handle-batch))

;; simulate a batch of records
(def records [{:id 1 :name "Alice"} {:id 2 :name "Bob"} {:id 3 :name "Charlie"}])

;; simulate a committer object
(def committer (proxy [EmbeddedEngine$RecordCommitter] [] (markBatchFinished [])))

;; process the batch of records using my-consumer
(.handleBatch my-consumer records committer)

(let [consumer (change-consumer handle-batch)]
  (. consumer handleBatch records committer))


  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  )
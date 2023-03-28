(ns rtburger.my-cdc-project
  (:import [io.debezium.config Configuration]
           [io.debezium.embedded EmbeddedEngine] 
           [io.debezium.embedded EmbeddedEngine$CompletionCallback]
           [io.debezium.embedded.spi OffsetCommitPolicy])
  
  (:require [clojure.core.async :refer [>! <! go chan]])) 

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
  {
   ; begin engine properties
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


; TODO impl interface CompletionCallback    
(defn create-complettion-callback
  ; Handle the completion of the embedded connector engine
  [f]
  (reify EmbeddedEngine$CompletionCallback
    (handle [_ _ message error] ;why does java method only have 3 params?
            (f message error)))
  )

; TODO impl interface ConnectorCallback
(defn create-connector-callback []
  ; A callback function which informs users about the various stages a connector goes through during startup
  ; 
  )

; TODO impl inteface RecordCommitter
(defn record-committer []
  ; Marks a single record as processed, must be called for each record 
  )

; TODO impl interface Offsets
(defn offsets []
  ; Associates a key with a specific value, overwrites the value if the key is already present

  )

; TODO impl interface ChangeConsumer
(defn change-consumer []
  ; handle a catch of records
  )

; TODO impl Builder
(defn create-engine [{:keys [config consumer] :as options}]
  ; A builder to setup and create Debezium Engine instance
  (let [builder (EmbeddedEngine/create)])
  )











(defn greet
  "Callable entry point to the application."
  [data]
  (println (str "Hello, " (or (:name data) "World") "!")))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (greet {:name (first args)}))






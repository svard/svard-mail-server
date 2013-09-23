(ns svard-mail-server.db
  (:refer-clojure :exclude [sort find])
  (:use [monger.query])
  (:require [monger.core :as mongo]
            [clojure.java.io :as io]))

(def mongo-db (:db (read-string (slurp (io/resource "server.conf")))))

(defn get-profiles
  []
  (mongo/connect! mongo-db)
  (mongo/set-db! (mongo/get-db "svard_mail"))
  (with-collection "profiles"
    (find {})))


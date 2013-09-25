(ns svard-mail-server.db
  (:refer-clojure :exclude [sort find])
  (:use [monger.query])
  (:require [monger.core :as mongo]
            [monger.collection :as mc]
            [clojure.java.io :as io]))

(def mongo-db (:db (read-string (slurp (io/resource "server.conf")))))

(defn- get-svard-mail-db
  "Returns a handler to the svard_mail database"
  []
  (mongo/connect! mongo-db)
  (mongo/set-db! (mongo/get-db "svard_mail")))

(defn- get-credentials
  "Get the credentials for the supplied user"
  [user]
  (get-svard-mail-db)
  (if-let [cred (mc/find-one-as-map "profiles" {:username user} ["username" "password" "roles"])]
    (update-in cred [:_id] str)
    nil))

(defn get-credential-map
  "Returns a credentials map as wanted by the authenticate function"
  [user]
  (if-let [cred (get-credentials user)]
    {:username user :password (:password cred) :roles (set (map (partial keyword "svard-mail-server.handler") (:roles cred)))}
    nil))
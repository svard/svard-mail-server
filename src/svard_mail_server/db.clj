(ns svard-mail-server.db
  (:refer-clojure :exclude [sort find])
  (:use [monger query operators])
  (:require [monger.core :as mongo]
            [monger.collection :as mc]
            [clojure.java.io :as io]))

(def mongo-db (:db (read-string (slurp (io/resource "server.conf")))))

(mongo/connect! mongo-db)
(mongo/set-db! (mongo/get-db "svard_mail"))

(defn get-credentials
  "Get the credentials for the supplied user"
  [user]
  (if-let [cred (mc/find-one-as-map "profiles" {:username user} ["username" "password" "roles"])]
    {:username user :password (:password cred) :roles (set (map (partial keyword "svard-mail-server.handler") (:roles cred)))}
    nil))

(defn add-contact
  "Adds one contact to the given user"
  [user contact]
  (mc/update "profiles" {:username user} {$push {:contacts {:name (:name contact) :address (:address contact)}}}))

(defn delete-contact
  "Deletes one contact from the given user"
  [user contact]
  (mc/update "profiles" {:username user} {$pull {:contacts {:name (:name contact) :address (:address contact)}}}))

(defn get-profile
  "Get profile for a given user"
  [user]
  (let [profile (mc/find-one-as-map "profiles" {:username user} ["contacts" "name" "roles" "username"])]
    (update-in profile [:_id] str)))
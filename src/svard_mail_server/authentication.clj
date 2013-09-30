(ns svard-mail-server.authentication
  (:use svard-mail-server.db)
  (:require (cemerick.friend [credentials :as creds])))

(defn verify-auth
  [{:keys [username password]}]
  (when-let [credentials (get-credentials username)]
    (when (creds/bcrypt-verify password (:password credentials))
      (-> credentials (dissoc :password) (assoc :password password)))))
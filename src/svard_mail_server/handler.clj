(ns svard-mail-server.handler
  (:use [compojure.core]
        [svard-mail-server template db])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [cemerick.friend :as friend]
            (cemerick.friend [credentials :as creds]
                             [workflows :as workflows])))

(defroutes app-routes
  (GET "/" request (friend/authorize #{::user} (index)))
  (GET "/login" request (login-template))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site 
    (friend/authenticate 
      app-routes
      {:login-uri "/login"
       :default-landing-uri "/"
       :credential-fn (partial creds/bcrypt-credential-fn get-credential-map)
       :workflows [(workflows/interactive-form)]})))

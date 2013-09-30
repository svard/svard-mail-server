(ns svard-mail-server.handler
  (:use [compojure.core]
        [ring.util.response]
        [svard-mail-server template authentication])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [cemerick.friend :as friend]
            (cemerick.friend [credentials :as creds]
                             [workflows :as workflows])))

(defroutes app-routes
  (GET "/" request (friend/authorize #{::user} (index)))
  (GET "/login" request (login-template))
  (GET "/logout" request (friend/logout* (redirect (str (:context request) "/"))))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site 
    (friend/authenticate 
      app-routes
      {:login-uri "/login"
       :default-landing-uri "/"
       :workflows [(workflows/interactive-form :credential-fn verify-auth)]})))
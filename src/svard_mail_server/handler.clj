(ns svard-mail-server.handler
  (:use [compojure.core]
        [svard-mail-server template db])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [cemerick.friend :as friend]
            (cemerick.friend [credentials :as creds]
                             [workflows :as workflows])))

(defroutes app-routes
  (GET "/" request (index))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(ns svard-mail-server.template
  (:use [net.cgrand.enlive-html :as html]))

(html/deftemplate index "public/index.html" [])

(html/deftemplate login-template "public/login.html" [])


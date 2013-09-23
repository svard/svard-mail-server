(defproject svard-mail-server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5" :exclusions [ring/ring-core org.clojure/core.incubator]]
                 [com.cemerick/friend "0.1.5"]
                 [org.clojure/data.json "0.2.3"]
                 [enlive "1.1.4"]
                 [com.novemberain/monger "1.5.0"]]
  :plugins [[lein-ring "0.8.7"]]
  :ring {:handler svard-mail-server.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})

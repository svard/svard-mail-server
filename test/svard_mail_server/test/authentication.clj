(ns svard-mail-server.test.authentication
  (:use clojure.test
        ring.mock.request  
        [svard-mail-server authentication db]))

(def db-mock {:username "test"
              :password "$2a$10$MGccYxpa6xd4dKxESC5EA.rOIIaMBnIYc4vwuhuwI9tIsY4jK0KEG"
              :roles #{:svard-mail-server.handler/user}})

(deftest test-authentication
  (testing "verify correct password"
    (let [auth-map (with-redefs [get-credentials (constantly db-mock)]
                     (verify-auth {:username "test" :password "test"}))]
      (is (= auth-map {:password "test", :username "test", :roles #{:svard-mail-server.handler/user}}))))
  
  (testing "fail wrong password"
    (let [auth-map (with-redefs [get-credentials (constantly db-mock)]
                     (verify-auth {:username "test" :password "incorrect"}))]
      (is (= auth-map nil)))))
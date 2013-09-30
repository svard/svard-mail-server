(ns svard-mail-server.test.handler
  (:use clojure.test
        ring.mock.request  
        [svard-mail-server handler db]))

(deftest test-app
  (testing "main route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 302))))
  
  (testing "login route"
    (let [response (app (request :get "/login"))]
      (is (= (:status response) 200))))
  
  (testing "not-found route"
    (let [response (app (request :get "/invalid"))]
      (is (= (:status response) 404)))))
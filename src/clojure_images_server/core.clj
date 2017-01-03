(ns clojure-images-server.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [immuconf.config])
  (:use [ring.util.response]))

(def cfg (immuconf.config/load "config.edn"))

(def my-routes
 (immuconf.config/get cfg :routes))

(defroutes serve-routes
  (apply routes
    (map
     #(GET
       (str (:servepath %) "/:file") [file]
         (resource-response file {:root (:fileserve %)}))
     my-routes)))

(defroutes app-routes
  (route/not-found (resource-response "404.html" {:root "static"})))

(def app (routes serve-routes app-routes))

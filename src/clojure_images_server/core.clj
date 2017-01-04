(ns clojure-images-server.core
  (:require [clojure.string :as str]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure-images-server.conf :as conf])
  (:use [ring.util.response]))

(defroutes serve-routes
  (apply routes
    (map
     #(GET
       (str (:fileserve %) "/:file") [file]
         (resource-response file {:root (:savepath %)}))
     conf/routes)))

(defroutes app-routes
  (route/not-found (resource-response "404.html" {:root "static"})))

(def app (routes serve-routes app-routes))

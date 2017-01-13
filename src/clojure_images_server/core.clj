(ns clojure-images-server.core
  (:require [clojure.string :as str]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure-images-server.conf :as conf]
            [clojure-images-server.save :as save]
            [ring.middleware.multipart-params :as mp]
            [ring.middleware.cors :refer [wrap-cors]])
  (:use [ring.util.response]))

(def my-routes conf/routes)

(defroutes save-routes
  (apply routes
    (map
     #(POST
       (:servepath %) req
       (save/handler req %))
     my-routes)))

(defroutes serve-routes
  (apply routes
    (map
     #(GET
       (str (:servepath %) "/:file") [file]
         (resource-response file {:root (:fileserve %)}))
     my-routes)))

(defroutes app-routes
  (route/not-found (resource-response "404.html" {:root "static"})))

(def app
  (-> (routes save-routes serve-routes app-routes)
      (wrap-cors :access-control-allow-origin [#".*"]
                  :access-control-allow-methods [:get :put :post :delete])))

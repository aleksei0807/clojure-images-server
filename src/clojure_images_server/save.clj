(ns clojure-images-server.save
  (:require [ring.middleware.multipart-params :as mp])
  (:use [ring.util.json-response]))

(def route-settings)

(defn- get-files [req]
  (get (:params (mp/multipart-params-request req)) "imageFiles"))

(defn- save-file [file]
  (:filename file))

(defn- save-files [files]
  (if (map? files)
      (list (save-file files))
      (if (:multiple route-settings)
        (map save-file files)
        (list (save-file (get files 0))))))

(defn handler [req rs]
  (do
    (def route-settings rs)
    (-> (get-files req)
        (save-files)
        (json-response))))

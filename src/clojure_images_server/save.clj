(ns clojure-images-server.save
  (:require [ring.middleware.multipart-params :as mp])
  (:use [ring.util.json-response]))

(defn- get-files [req]
  (get (:params (mp/multipart-params-request req)) "imageFiles"))

(defn- save-file [file]
  (:filename file))

(defn- save-files [rs files]
  (do (println rs)
    (if (map? files)
        (list (save-file files))
        (if (:multiple rs)
          (map save-file files)
          (list (save-file (get files 0)))))))

(defn handler [req rs]
  (-> (get-files req)
      ((partial save-files rs))
      (json-response)))

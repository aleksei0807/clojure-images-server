(ns clojure-images-server.save
  (:require [ring.middleware.multipart-params :as mp]
            [clojure.string :as string]
            [digest])
  (:use [ring.util.json-response]
        [clojure.java.io]))


(defn- get-files [req]
  (get (:params (mp/multipart-params-request req)) "imageFiles"))

(defn- save-file [rs file]
  (let [rename? (:rename rs)
        tempfile (:tempfile file)
        original-name (:filename file)
        dot (string/last-index-of original-name ".")
        ext (subs original-name dot)
        name (if rename?
               (str (digest/md5 tempfile) ext)
               (:filename file))
        savepath (str (:savepath rs) "/" name)
        fullpath (str (:fullpath rs) "/" name)]
    (do (when-not (.exists (as-file savepath))
          (copy tempfile (java.io.File. savepath)))
      fullpath)))


(defn- save-files [rs files]
  (let [save-file (partial save-file rs)]
    (if (map? files)
        (list (save-file files))
        (if (:multiple rs)
          (map save-file files)
          (list (save-file (get files 0)))))))

(defn handler [req rs]
  (-> (get-files req)
      ((partial save-files rs))
      (json-response)))

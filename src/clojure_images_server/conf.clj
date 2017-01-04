(ns clojure-images-server.conf
  (:require [clojure.string :as str]
            [immuconf.config :as conf]))

(def cfg (conf/load "config.edn"))

(defn- add-slash [m k]
  (if
   (str/starts-with? (k m) "/")
   m
   (update m k (partial str "/"))))

(defn- trim-right-slash [m k]
  (update m k str/replace #"(?<=.)/$" ""))

(def routes
  (map
    #(-> (add-slash % :fileserve)
         (add-slash :servepath)
         (trim-right-slash :savepath)
         (trim-right-slash :fullpath))
    (conf/get cfg :routes)))

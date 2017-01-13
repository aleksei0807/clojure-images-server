(defproject clojure-images-server "0.1.0"
  :description "Server for react-images-uploader"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-core "1.5.0"]
                 [ring "1.5.0"]
                 [compojure "1.5.1"]
                 [levand/immuconf "0.1.0"]
                 [ring-json-response "0.2.0"]
                 [ring-cors "0.1.8"]
                 [digest "1.4.5"]]
  :plugins [[lein-ring "0.10.0"]]
  :ring {:handler clojure-images-server.core/app
         :auto-reload? true
         :auto-refresh? true
         :reload-paths ["src/clojure_images_server"]})

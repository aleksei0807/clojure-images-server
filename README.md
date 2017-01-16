# clojure-images-server

Server for [react-images-uploader](https://github.com/aleksei0807/react-images-uploader)

## Usage

```
lein deps
lein ring server 9090
```

## Config

- routes - object with your routes
  - servepath - serve path
  - savepath - path where you want to save images
  - fullpath - full path to folder with images
  - fileserve - serve images path
  - multiple - allows to upload a bunch of images `default: false`
  - rename - if false, then do not rename image `default: true`

## Config example

```clojure
{:routes [{:servepath "/multiple"
           :savepath "resources/static/multipleFiles"
           :fullpath "//localhost:9090/multiple"
           :fileserve "static/multipleFiles"
           :multiple true
           :rename true}
          {:servepath "/notmultiple"
           :savepath "resources/static/files"
           :fullpath "//localhost:9090/notmultiple"
           :fileserve "static/files"
           :multiple false
           :rename true}]}
```

## License

Copyright © 2017 Aleksei Schurak

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

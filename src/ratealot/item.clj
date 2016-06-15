(ns ratealot.item
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]
            [ratealot.review :refer :all]))

(s/defschema Item {:barcode String
                   :name String
                   :description String
                   })

(defonce items (atom (array-map)))

(defn get-item
  "Get an item by barcode. If it doesn't exist, return nil"
  [barcode]
  (@items barcode)
  )

(defn add! [new-item]
  item (coerce! Item new-item))

(when (empty? @item)
  (add! {:barcode "abc123" :name "Camel Filter" :description "Toasted lekker cigs"}))

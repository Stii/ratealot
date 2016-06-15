(ns ratealot.item
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]
            [ratealot.review :refer :all]
            [taoensso.carmine :as car :refer (wcar)]))

(s/defschema Item {:barcode String
                   :name String
                   :description String
                   :reviews [Review]
                   })

(def server1-conn {:pool {} :spec {}})
(defmacro wcar* [& body] `(car/wcar server1-conn ~@body))

(def make-item-key (partial str "items:"))
(defn make-key-from-item [item]
  (let [ barcode (:barcode item) ]
    (make-item-key barcode)))

(defn get-item
  "Get an item by barcode. If it doesn't exist, return nil"
  [barcode]
  ;;(:barcode @items barcode))
  (let [ item-key (make-item-key barcode)
         key-getter (partial car/hmget item-key)
        item-names (map name (keys Item) )
        item-value (wcar* (apply key-getter item-names))
        [barcode name description]  item-value ]
    (coerce! Item {:barcode barcode
                   :name name
                   :description description
                   :reviews (get-reviews barcode)})))

(defn add! [new-item]
  (let [item (coerce! Item new-item)
        barcode (:barcode new-item)
        description (:description new-item)
        name (:name new-item)
        item-key (make-key-from-item new-item) ]
    (wcar* (car/hmset item-key
                      "barcode" barcode
                      "name" name
                      "description" description)) ))
    

(comment 
  (do
    (add! {:barcode "abc123" :name "Camel Filteragain" :description "Toasted lekker cigs" :reviews [] })
    (add! {:barcode "abc122" :name "Camel Filter STUFF" :description "Toasted lekker cigs" :reviews []})
    (add! {:barcode "abc125" :name "TEXAN" :description "Toasted lekker cigs ALSO sssYADAD" :reviews []})
    (add-review! "abc123" {:reviewer "Bob" :rating (Integer. 4) :review "Shitty smokes" :date-added "now"})
    (add-review! "abc123" {:reviewer "Bob" :rating (Integer. 4) :review "Shitty smokes" :date-added "now"})
    (add-review! "abc122" {:reviewer "Paul" :rating (Integer. 10) :review "Greate smokes!" :date-added "now"}))
  

    )
(get-reviews "abc122")
   (get-item "abc125")
(keys Item)
  
  )

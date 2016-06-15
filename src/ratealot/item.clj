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
(comment
  ;; (wcar* (car/ping)) ; => "PONG" (1 command -> 1 reply)
  
  ;; (wcar*
  ;;   (car/ping)
  ;;   (car/set "foo" "bar")
  ;;   (car/get "foo")) ; => ["PONG" "OK" "bar"] (3 commands -> 3 replies)
  )

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
    {:barcode barcode :name name :description description}))

<<<<<<< HEAD
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
  (when (empty? @items)
    (add! {:barcode "abc123" :name "Camel Filteragain" :description "Toasted lekker cigs" :reviews [] })
    (add! {:barcode "abc122" :name "Camel Filter STUFF" :description "Toasted lekker cigs" :reviews []})
    (add! {:barcode "abc125" :name "TEXAN" :description "Toasted lekker cigs ALSO sssYADAD" :reviews []}))
  (get-item "abc125")
(keys Item)
  
  )
=======
(defn add!
  "Add a new item."
  [new-item]
  (let [item (coerce! Item new-item)]
    (swap! items conj item)
    item))

(defn add-review!
  "Add a review for an item by barcode"
  [barcode review]
  )
(when (empty? @items)
  (add! {:barcode "abc123" :name "Camel Filter" :description "Toasted lekker cigs" :reviews '[]}))
>>>>>>> add reviews function

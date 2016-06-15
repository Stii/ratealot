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

(defn get-item
  "Get an item by barcode. If it doesn't exist, return nil"
  [barcode]
  (:barcode @items barcode))

(defn add! [new-item]
  (let [item (coerce! Item new-item)
        barcode (:barcode new-item)]
    (wcar* (car/hmset (str "items:" barcode)
                      "barcode" (:barcode new-item)
                      "name" (:name new-item)
                      "description" (:description new-item)))
    ))

(comment 
  (when (empty? @items)
    (add! {:barcode "abc123" :name "Camel Filteragain" :description "Toasted lekker cigs" :reviews '[]})
    (add! {:barcode "abc122" :name "Camel Filter STUFF" :description "Toasted lekker cigs" :reviews '[]})
    (add! {:barcode "abc125" :name "TEXAN" :description "Toasted lekker cigs" :reviews '[]}))
  
  )

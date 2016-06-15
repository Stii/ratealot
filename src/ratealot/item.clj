(ns ratealot.item
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]
            [ratealot.review :refer :all]
            [taoensso.carmine :as car :refer (wcar)]))

(s/defschema Aggregate {:average Double
                        :count Integer
                        })

(s/defschema Item {:barcode String
                   :name String
                   :description String
                   :reviews [Review]
;;                   :aggregate Aggregate
                   })

;; (def server1-conn {:pool {} :spec {}})
;; (defmacro wcar* [& body] `(car/wcar server1-conn ~@body))

(def make-item-key (partial str "item:"))

(defn make-key-from-item [item]
  (let [ barcode (:barcode item) ]
    (make-item-key barcode)))

(defn aggregate
  [reviews]
  (if (empty? reviews)
    {:average (Double. 0.0) :count (Integer. 0)}
    (let [rating-count (count reviews)
          rating-total (reduce + (map :rating reviews))]
      (if (and (> rating-total 0) (> rating-count 0))
        {:average (float (/ rating-total rating-count)) :count (Integer. rating-count)}
        {:average 0.0 :count 0}
        ))))

(defn get-item
  "Get an item by barcode. If it doesn't exist, return nil"
  [barcode]
  ;;(:barcode @items barcode))
  (let [item-key (make-item-key barcode)
        key-getter (partial car/hmget item-key)
        item-names (map name (keys Item) )
        item-value (wcar* (apply key-getter item-names))
        [barcode name description]  item-value 
        reviews (get-reviews barcode)]
    (if (not (empty? item-value))
      (coerce! Item {:barcode barcode
                     :name name
                     :description description
                     :reviews reviews}))))

(defn add! [new-item]
  (let [item (coerce! Item new-item)
        barcode (:barcode new-item)
        description (:description new-item)
        name (:name new-item)
        item-key (make-key-from-item new-item) ]
    (wcar* (car/hmset item-key
                      "barcode" barcode
                      "name" name
                      "description" description)) 
    (get-item barcode)))

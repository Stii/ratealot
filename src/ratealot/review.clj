(ns ratealot.review
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]
            [taoensso.carmine :as car :refer (wcar)]))


(s/defschema Review {:reviewer String
                     :rating Integer
                     :review String
                     :date-added String})

(def server1-conn {:pool {} :spec {}})
(defmacro wcar* [& body] `(car/wcar server1-conn ~@body))

(defn new-review-id []
  (wcar* (car/incr "new_review_id")))

(defn add-review!
  [barcode new-review]
  (let [review-id (str "review:" (new-review-id))
        review (coerce! Review new-review)
        item-key (str "list:item:" barcode)]
    (wcar* (car/hmset review-id 
                      "reviewer" (:reviewer review)
                      "rating" (:rating review)
                      "review" (:review review)))
    (wcar* (car/lpush item-key review-id))
    review-id
    ))

(defn get-review-ids
  [barcode]
  (let [item-key (str "list:item:" barcode)]
    (wcar* (car/lrange item-key 0 -1))))

(defn get-review
  [review-key]
  (let [key-getter (partial car/hmget review-key)
        item-vals (wcar* (apply key-getter (map name (keys Review))))
        [reviewer rating review date-added] item-vals]
    (print ">>" rating)
    (coerce! Review {:reviewer reviewer 
                     :rating (Integer. rating) 
                     :review review 
                     :date-added (if (nil? date-added)
                                   ""
                                   date-added)})))

(defn get-reviews
  [barcode]
  (map (fn [key] (get-review key)) (get-review-ids barcode)))

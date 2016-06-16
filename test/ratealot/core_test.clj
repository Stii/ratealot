(ns ratealot.core-test
  (:require [ratealot.item :refer :all]
            [ratealot.review :refer :all]
            [clojure.test ]
            ;;[taoensso.carmine :as car]
            ;; [ring.swagger.schema :refer [coerce!]]
            ))
  


(clojure.test/deftest test123
  (println "BLA")
  (with-redefs [   car/wcar (fn [ server  & body] (println ">> server body" server body))
                car/hmget (fn [ key values ] (println ">> key values" key values))
                car/hmset (fn [ key values & args ] (println ">> key values args" key values args))
                ]
    (add! {:barcode "abc123" :name "Camel Filteragain" :description "Toasted lekker cigs" :reviews [] })))

;; (comment
;;   (do
;;      (add! {:barcode "abc123" :name "Camel Filteragain" :description "Toasted lekker cigs" :reviews [] })
;;      (add! {:barcode "abc122" :name "Camel Filter STUFF" :description "Toasted lekker cigs" :reviews []})
;;      (add! {:barcode "abc125" :name "TEXAN" :description "Toasted lekker cigs ALSO sssYADAD" :reviews []})
;;      (add-review! "abc123" {:reviewer "Bob" :rating (Integer. 4) :review "Sucky smokes" :date-added "now"})
;;      (add-review! "abc123" {:reviewer "Bob" :rating (Integer. 4) :review "Sucky smokes" :date-added "now"})
;;      (add-review! "abc122" {:reviewer "Paul" :rating (Integer. 10) :review "Greate smokes!" :date-added "now"}))
;;   )

(ns ratealot.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [ratealot.item :refer :all]))

(defapi app
    {:swagger
     {:info {:title "Ratealot Api"
             :description "Compojure Api for doing ratings and reviews"}
      :tags [{:name "api", :description "Find items and reviews by barcode"}]}}

    (context "/api" []
      :tags ["api"]

      (GET "/:barcode" []
        :return Item
        :query-params [barcode :- String]
        :summary "Gets a item by barcode"
        (ok (get-item barcode)))))

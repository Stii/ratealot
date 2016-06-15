(ns ratealot.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [ratealot.item :refer :all]
            [ratealot.review :refer :all]))

(def app
  (api
    {:swagger
     {:ui "/doc"
      :spec "/swagger.json"
      :data {:info {:title "Ratealot"
                    :description "Compojure Api for doing reviews and ratings. alot"}
             :tags [{:name "api", :description "ratings api"}]}}}

    (context "/api" []
      :tags ["api"]

      (GET "/:barcode" []
        :return {:result Long}
        :query-params [x :- Long, y :- Long]
        :summary "adds two numbers together"
        (ok {:result (+ x y)}))

      (POST "/echo" []
        :return Pizza
        :body [pizza Pizza]
        :summary "echoes a Pizza"
        (ok pizza)))))

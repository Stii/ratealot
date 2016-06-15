(ns ratealot.data
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]))

(def Score (s/enum 1 2 3 4 5))

(s/defschema Item {:gtin String
                   :name String
                   :description String
                   :image String
                   })

(s/defschema Review {
})

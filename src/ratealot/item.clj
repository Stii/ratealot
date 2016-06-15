(ns ratealot.item
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]))

(s/defschema Item {:gtin String
                   :name String
                   :description String
                   :image String
                   })

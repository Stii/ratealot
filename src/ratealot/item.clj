(ns ratealot.item
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]
            [ratealot.review :refer :all]))

(s/defschema Item {:id String
                   :name String
                   :description String
                   })

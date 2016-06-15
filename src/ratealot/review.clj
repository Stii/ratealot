(ns ratealot.review
  (:require [schema.core :as s]
            [ring.swagger.schema :refer [coerce!]]))

(def Score (s/enum 1 2 3 4 5))

(s/defschema Review {:reviewer String
                     :rating Integer
                     :review String
                     :date-added String})

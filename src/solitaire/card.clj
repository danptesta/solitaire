(ns solitaire.card)

(def suits #{:diamonds :hearts :clubs :spades})

(def values #{:A :2 :3 :4 :5 :6 :7 :8 :9 :10 :J :Q :K})

(defn make [suit value]
  (assert (contains? values value))
  (assert (contains? suits suit))
  [suit value])

(defn suit [[suit _]]
  suit)

(defn value [[_ value]]
  value)

(def numeric-values {:A 1
                     :2 2
                     :3 3
                     :4 4
                     :5 5
                     :6 6
                     :7 7
                     :8 8
                     :9 9
                     :10 10
                     :J 11
                     :Q 12
                     :K 13})

(defn value-numeric-value [value]
  (numeric-values value))

(defn numeric-value [card]
  (value-numeric-value (value card)))

(def suit-colors {:diamonds :red :hearts :red :clubs :black :spades :black})

(defn suit-color [suit]
  (suit-colors suit))

(defn color [card]
  (suit-color (suit card)))

(def deck (for [suit suits
                value values]
            (make suit value)))

(count deck)

(ns solitaire.core-test
  (:require [clojure.test :refer :all]
            [solitaire.core :refer :all]
            [solitaire.card :as card]))

(deftest tableau0-tableau1
  (let [game {:tableau [{:up (list
                              (card/make :diamonds :3)
                              (card/make :clubs :4)
                              (card/make :hearts :5))}
                        {:up (list
                              (card/make :spades :6))}]}
        game2 {:tableau [{:up (list)}
                         {:up (list
                               (card/make :diamonds :3)
                               (card/make :clubs :4)
                               (card/make :hearts :5)
                               (card/make :spades :6))}]}
        move (fn [game]
               (let [count 3
                     from [:tableau 0 :up]
                     to [:tableau 1 :up]
                     cards (take count (get-in game from))]
                 (-> game
                     (update-in from (partial drop count))
                     (update-in to into (reverse cards)))))]
    (is (= game2 (move game)))))

;; scratch pad

(def dan {:tableau [{:up (list
                           (card/make :diamonds :3)
                           (card/make :clubs :4)
                           (card/make :hearts :5))}
                     {:up (list
                           (card/make :spades :6))}]})


(-> dan :tableau first :up)

(-> dan
    (update-in [:tableau 0 :up] (partial drop 3))
    (update-in [:tableau 1 :up] into (reverse (take 3 (get-in dan [:tableau 0 :up])))))

(defn hello [x] (str "Hello, " x "!"))
(-> "Dan"
    clojure.string/join
    (hello)
    .toUpperCase)

;; end scratch pad

(deftest tableau0-foundation0
  (let [game {:foundations [{:up (list
                                  (card/make :diamonds :2)
                                  (card/make :diamonds :A))}]
              :tableau [{:up (list
                              (card/make :diamonds :3)
                              (card/make :clubs :4)
                              (card/make :hearts :5)
                              (card/make :spades :6))}]}
        game2 {:foundations [{:up (list
                                  (card/make :diamonds :3)
                                  (card/make :diamonds :2)
                                  (card/make :diamonds :A))}]
              :tableau [{:up (list
                              (card/make :clubs :4)
                              (card/make :hearts :5)
                              (card/make :spades :6))}]}
        move (fn [game]
               (let [count 1
                     from [:tableau 0 :up]
                     to [:foundations 0 :up]
                     cards (take count (get-in game from))]
                 (-> game
                     (update-in from (partial drop count))
                     (update-in to into (reverse cards)))))]
    (is (= game2 (move game)))))

(deftest tableau3-tableau0
  (let [game { :tableau [{:up (list
                              (card/make :diamonds :3)
                              (card/make :clubs :4)
                              (card/make :hearts :5)
                              (card/make :spades :6))}
                         {:up (list
                               (card/make :clubs :K))}
                         {:up (list
                               (card/make :spades :2))}]}
        game2 { :tableau [{:up (list
                              (card/make :spades :2)
                              (card/make :diamonds :3)
                              (card/make :clubs :4)
                              (card/make :hearts :5)
                              (card/make :spades :6))}
                          {:up (list
                                (card/make :clubs :K))}
                          {:up (list)}]}
        move (fn [game]
               (let [count 1
                     from [:tableau 2 :up]
                     to [:tableau 0 :up]
                     cards (take count (get-in game from))]
                 (-> game
                     (update-in from (partial drop count))
                     (update-in to into (reverse cards)))))]
    (is (= game2 (move game)))))

(deftest stock-tableau1
  (let [game { :stock {:up (list
                            (card/make :hearts :Q)
                            (card/make :diamonds :7)
                            (card/make :clubs :10))}
               :tableau [{:up (list
                              (card/make :diamonds :3)
                              (card/make :clubs :4)
                              (card/make :hearts :5)
                              (card/make :spades :6))}
                         {:up (list
                               (card/make :clubs :K))}
                         {:up (list
                               (card/make :spades :2))}]}
        game2 { :stock {:up (list
                             (card/make :diamonds :7)
                             (card/make :clubs :10))}
                :tableau [{:up (list
                              (card/make :diamonds :3)
                              (card/make :clubs :4)
                              (card/make :hearts :5)
                              (card/make :spades :6))}
                          {:up (list
                                (card/make :hearts :Q)
                                (card/make :clubs :K))}
                          {:up (list
                                (card/make :spades :2))}]}
        move (fn [game]
               (let [count 1
                     from [:stock :up]
                     to [:tableau 1 :up]
                     cards (take count (get-in game from))]
                 (-> game
                     (update-in from (partial drop count))
                     (update-in to into (reverse cards)))))]
    (is (= game2 (move game)))))

(deftest draw-stock
  (let [game { :stock {:down (list
                              (card/make :hearts :Q)
                              (card/make :diamonds :7)
                              (card/make :clubs :10)
                              (card/make :spades :3))
                       :up   (list
                              (card/make :diamonds :8)
                              (card/make :clubs :J))}}
        game2 { :stock {:down (list
                               (card/make :spades :3))
                        :up (list
                             (card/make :clubs :10)
                             (card/make :diamonds :7)
                             (card/make :hearts :Q)
                             (card/make :diamonds :8)
                             (card/make :clubs :J))}}
        move (fn [game]
               (let [count 3
                     from [:stock :down]
                     to [:stock :up]
                     cards (take count (get-in game from))]
                 (-> game
                     (update-in from (partial drop count))
                     (update-in to into cards))))]
    (is (= game2 (move game)))))

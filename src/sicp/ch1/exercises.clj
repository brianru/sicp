(ns sicp.ch1.exercises)

;; Exercise 1.1

(assert (= 10 10))

(assert (= (+ 5 3 4) 12))

(assert (= (- 9 1) 8))

(assert (= (/ 6 2) 3))

(def a 3)

(def b (+ a 1))

(assert (= (+ a b (* a b)) 19))

(assert (= b
           (if (and (> b a) (< b (* a b)))
             b
             a)))

(assert (= (+ 2 (if (> b a) b a)) 6))

(assert (= (* (cond
                (> a b) a
                (< a b) b
                :else   -1)
              (+ a 1))
           16))

;; Exercise 1.2

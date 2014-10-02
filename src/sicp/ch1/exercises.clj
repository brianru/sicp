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

(assert (= (/ (+ 5 4 (- 2 (- 3 (+ 6 4/3))))
              (* 3 ( - 6 2) (- 2 7)))
           -23/90))

;; Exercise 1.3

(defn one-three [& xs]
  (let [[a b & cs] (sort > xs)]
    (+ (* a a) (* b b))))

(assert (= 13 (one-three 1 2 3)))

;; Exercise 1.4

(defn a-plus-abs-b
  "The conditional determines whether the addition or negation function
  should be used based on the sign of b. If b is negative the negation
  funciton is used which negates the engative number. In this way the
  operation effectively takes the absolute value of b by switching
  operators to ensure its positive when combined with a."
  [a b]
  ((if (> b 0) + -)
   a b))

;; Exercise 1.5

(defn p [] (p))

(defn test
  "Applicative Order: the operands are evaluated immediately. This would
  cause infinite recursion and a stack overflow error.
  
  Normal Order: evaluation is suspended until all symbols are substituded
  for primitives. If statements suspend branch evaluation until the
  branch is taken per the result of the predicate function. Therefore,
  the branch of this test function that uses '(p)' would not be evaluated
  and the result of zero would be produced.
  
  Clojure uses something like applicative order."
  [x y]
  (if (= x 0) 0 y))

(test 0 (p)) ;; produces a StackOverflow Error

;; Exercise 1.6
;; Using a function wrapper around cond causes the else-clause to be
;; evaluated regardless of the predicate expression's value. This
;; leads to a stack overflow error.

;; Exercise 1.7
(defn average [x y]
  (/ (+ x y) 2))

(defn good-enough?
  "For small numbers the good-enough predicate would be triggered too
  soon relative to the size of the radicand. Arithmetic for smaller
  numbers should be calculated with a degree of precision relative
  to their size. For large numbers this would be too accurate and may
  take way too long unnecessarily. This test works better for both small
  and large numbers by setting its desired precision relative to the
  absolute size of the square root."
  [g g*]
  (> (* 0.0000001 g) (Math/abs (- g g*))))

(defn improve-sqrt [x g]
  (average g (/ x g)))

(defn sqrt
  ([x]
   (sqrt x (list 1.0)))
  ([x [g & _ :as guesses]]
   (let [g* (improve-sqrt x g)]
     (if (good-enough? g g*)
       g*
       (recur x (cons g* guesses))))))

(defn improve-cbrt [x g]
  (/ (+ (/ x
           (* g g))
        (* 2 g))
     3.0))

;; Exercise 1.8
(defn cbrt
  ([x]
   (cbrt x (list 1.0)))
  ([x [g & _ :as guesses]]
   (let [g* (improve-cbrt x g)]
     (if (good-enough? g g*)
       g*
       (recur x (cons g* guesses))))))

;; Exercise 1.9
;; The first implementation produces a recursive process as the
;; expression nests within itself as it recurses.

;; The second implementation produces an interative process as all
;; necessary state is held within the fn arguments and no further
;; operations are performed on the result of the recursive expression.

;; This shows two functions can have the same arguments but that does
;; not suffice for the functions to carry the full state of the
;; process they produce in the recursive function calls.


;; Exercise 1.10
(defn A [x y]
  (cond (= y 0) 0
        (= x 0) (* 2 y)
        (= y 1) 2
        :else   (A (- x 1)
                   (A x (- y 1)))))

(assert (= (A 1 10) 1024))

(assert (= (A 2 4) 65536))

(assert (= (A 3 3) 65536))

(defn A-f
  "f(n) = 2n"
  [n]
  (A 0 n))
(map A-f (range 0 10))

(defn A-g
  "g(n) = 2^n except for n=0, which gives you 0"
  [n]
  (A 1 n))
(map A-g (range 0 20))

(defn A-h
  "h(g) = 2^2^... (n times)
  when n = 0,     0, 2^2^... (n times))
  when n = 1,     2, 2^1, 2^n - 
  when n = 2,     4, 2^2, 2^n 
  when n = 3,    16, 2^4, 2^n
  when n = 4, 65536, 2^16"
  [n]
  (A 2 n))
(map A-h (range 0 5))

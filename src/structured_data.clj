(ns structured-data)

(defn do-a-thing [x]
  (let [thing (+ x x)]
    (Math/pow thing thing)))

(defn spiff [v]
  (let [first (get v 0)
        second (get v 2)]
    (+ first second)))

(defn cutify [v]
  (conj v "<3"))

(defn spiff-destructuring [v]
  (let [[a ? b] v]
    (+ a b)))

(defn point [x y]
  [x y])

(defn rectangle [bottom-left top-right]
  [bottom-left top-right])

(defn width [rectangle]
  (let [[[x1 ?] [x2 ?]] rectangle]
    (- x2 x1)))

(defn height [rectangle]
  (let [[[? y1] [? y2]] rectangle]
    (- y2 y1)))

(defn square? [rectangle]
  (== (height rectangle) (width rectangle)))

(defn area [rectangle]
  (* (height rectangle) (width rectangle)))

(defn contains-point? [rectangle point]
  (let [[[x1 y1] [x2 y2]] rectangle
        [a b] point
        left-in (>= a x1)
        right-in (<= a x2)
        bottom-in (>= b y1)
        top-in (<= b y2)]
    (and left-in right-in bottom-in top-in)
    ))

(defn contains-rectangle? [outer inner]
  (let [[bottom-left top-right] inner
        bottom-left-in (contains-point? outer bottom-left)
        top-right-in (contains-point? outer top-right)]
    (and bottom-left-in top-right-in)
    ))

(defn title-length [book]
  (count (:title book)))

(defn author-count [book]
  (count (:authors book)))

(defn multiple-authors? [book]
  (> (author-count book) 1))

(defn add-author [book new-author]
  (let [new-authors (conj (:authors book) new-author)]
    (assoc book :authors new-authors)))

(defn alive? [author]
  (not (contains? author :death-year)))

(defn element-lengths [collection]
  (map count collection))

(defn second-elements [collection]
  (let [second
        (fn [coll] (get coll 1))]
    (map second collection)))

(defn titles [books]
  (map :title books))

(defn stars [n]
  (apply str (repeat n "*")))

(defn monotonic? [a-seq]
  (or (apply <= a-seq) (apply >= a-seq)))

(defn toggle [a-set elem]
  (if (contains? a-set elem)
    (disj a-set elem)
    (conj a-set elem)))

(defn contains-duplicates? [a-seq]
  (< (count (set a-seq))
     (count a-seq)))

(defn old-book->new-book [book]
  (let [authors (set (:authors book))]
    (assoc book :authors authors)))

(defn has-author? [book author]
  (contains? (:authors book) author))

(defn authors [books]
  (set (apply concat (map :authors books))))

(defn all-author-names [books]
  (set (map :name (authors books))))

(defn author->string [author]
  (let [life-info
        (fn [author] (if (or (:birth-year author) (:death-year author))
                       (str " (" (:birth-year author) " - " (:death-year author) ")")))]
    (apply str (:name author) (life-info author))))

(defn authors->string [authors]
  (apply str (interpose ", " (map author->string authors))))

(defn book->string [book]
  (apply str (interpose ", written by " [(:title book), (authors->string (authors [book]))])))

(defn books->string [books]
  (cond
    (empty? books) "No books."
    (= (count books) 1) (str "1 book. " (book->string (get books 0)) ".")
    :else (str (count books) " books. " (apply str (interpose ". " (map book->string books))) ".")))

(defn books-by-author [author books]
  (filter (fn [book] (has-author? book author)) books))

(defn author-by-name [name authors]
  (let [results (filter (fn [author] (= name (:name author))) authors)]
    (if (empty? results)
      nil
      (first results)))
  )

(defn living-authors [authors]
  (filter (fn [author] (alive? author)) authors))

(defn has-a-living-author? [book]
  (< 0 (count (living-authors (:authors book)))))

(defn books-by-living-authors [books]
  (filter (fn [current] (has-a-living-author? current)) books))


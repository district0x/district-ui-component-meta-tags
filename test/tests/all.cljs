(ns tests.all
  (:require [cljs.spec.alpha :as s]
            [cljs.test :refer [deftest is testing run-tests async use-fixtures]]
            [cljs-react-test.simulate :as simulate]
            [cljs-react-test.utils :as test-utils]
            [hickory.core :as hickory]
            [day8.re-frame.test :refer [run-test-async run-test-sync wait-for]]
            [district.ui.reagent-render]
            [district.ui.component.meta-tags :as meta-tags]
            [mount.core :as mount :refer [defstate]]
            [re-frame.core :as re-frame]))

(s/check-asserts true)

(def container (atom nil))

(def tags {:title {:title "title" :description "description"}
           :meta-0 {:id "id0" :name "foo" :content "foo"}
           :meta-1 {:id "id1" :name "bar" :content "bar"}})

(defn mock-html []
  [:div#app [meta-tags/meta-tags (:title tags)
             (:meta-0 tags)
             (:meta-1 tags)]])

(use-fixtures :each
  {:before #(do (reset! container (test-utils/new-container!))
                (-> (mount/with-args {:reagent-render {:target @container
                                                       :component-var #'mock-html}})
                    (mount/start)))
   :after #(do (test-utils/unmount! @container)
               (mount/stop))})

(deftest tests
  (testing "test meta tags"
    (let [get-meta (fn [index] (-> (aget js/document "head")
                                   .-innerHTML
                                   hickory/parse
                                   hickory/as-hiccup
                                   first
                                   (nth 2)
                                   (nth index)
                                   second))]
      (async done
             (.setTimeout js/window
                          (fn []

                            (is (= (-> tags :title :title)
                                     (:id (get-meta 6))))

                            (is (= (:meta-0 tags)
                                   (get-meta 8)))

                            (is (= (:meta-1 tags)
                                   (get-meta 9)))

                            (done))
                          1000)))))

(ns core
  (:require [leva.core :as leva]
            [reagent.core :as r]
            [reagent.dom :as rdom]))

(defonce controls (r/atom {:width 500 :height 500}))

(defn app []
  [:div
   [leva/SubPanel {:fill true :titleBar {:drag false}}
    [leva/Controls {:folder {:name "Plot Controls"} :atom controls}]]])

;;; Lifecycle / entry point

(defn start [] (rdom/render [app] (.getElementById js/document "app")))
(defn stop [] (js/console.log "stopping..."))
(defn ^:export init [] (start))

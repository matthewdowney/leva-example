(ns core
  (:require [leva :as leva-js]
            ["react" :as react]
            [leva.core :as leva-cljs]
            [leva.schema :as schema]
            [reagent.core :as r]
            [reagent.dom :as rdom]))

(defonce controls (r/atom {:width 500 :height 500}))

(defn Controls* [opts]
  (let [[watch-id] (react/useState (str (random-uuid)))
        !state     (:atom opts)
        initial    (if !state (.-state !state) {})
        ks         (keys initial)
        opts       (update opts :store #(or % (leva-js/useStoreContext)))
        [_ set] (apply leva-js/useControls (schema/opts->argv opts))]
    (react/useEffect
      (fn mount []
        (if !state
          (do
            (add-watch !state watch-id
              (fn [_ _ _ new-state]
                (set (clj->js (select-keys new-state ks)))))
            (fn unmount []
              (remove-watch !state watch-id)))
          js/undefined)))
    nil))

(defn app []
  [:f> leva-cljs/SubPanel {:fill true :titleBar {:drag false}}
   [:f> Controls* {:folder {:name "Plot Controls"} :atom controls}]])

;;; Lifecycle / entry point

(defn start [] (rdom/render [app] (.getElementById js/document "app")))
(defn ^:export init [] (start))

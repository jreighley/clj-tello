(defproject clj-tello "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [mount "0.1.16"]]
  :main ^:skip-aot clj-tello.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

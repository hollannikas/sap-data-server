(defproject test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.zip "0.1.3"]
                 [org.clojure/data.xml "0.0.8"]
                 [korma "0.4.3"]
                 [org.postgresql/postgresql "42.2.5.jre7"]
                 [amazonica "0.3.140"]
                 [cheshire "5.8.1"]]
  :main ^:skip-aot tylsimys.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})

(ns clj-tello.core
  (:require [clj-tello.udp :as drone]
            [mount.core :refer [defstate] :as mount]
            [clojure.string :as s]))
(declare drone-send tello-command-socket start-tello)
(def tello-address "192.168.10.1")
(def tello-state (atom {}))

(defn command "Transmits a command to Tello via UDP"
  [command]
  (drone/send tello-command-socket (str "command") tello-address 8889)
  (drone/send tello-command-socket command tello-address 8889))

(defstate tello-command-socket :start (drone/create-socket 8889)
                               :stop (.close tello-command-socket))
(defstate tello-state-socket :start (drone/create-socket 8890)
                             :stop (.close tello-state-socket))
(defstate tello-video-socket :start (drone/create-socket 11111)
                             :stop (.close tello-video-socket))
(defstate tello-video-stream :start (command "streamon")
                             :stop (command "streamoff"))
(defstate udp-listeners :start (start-tello))


(defn parse-state-kv
  "Converts string 'key:val' provided by tello state to edn map"
  [kv]
  (let [status-kv (s/split  kv #":")]
   (hash-map (keyword (first status-kv )) (nth status-kv 1))))

(defn update-state! "Updates the state atom according to the string returned by the tello." [state-str]
  (let [attr-list (s/split state-str #";")]
    (->> attr-list
      (drop-last 1)
      (map parse-state-kv)
      (reduce conj)
      (reset! tello-state))))

(defn start-tello []
  (try (drone/receive-loop tello-command-socket println))
  (try (drone/receive-loop tello-state-socket update-state!))
  (command "battery?"))

(defn -main
  "fires up the UDP channels"
  []
  (mount/start))


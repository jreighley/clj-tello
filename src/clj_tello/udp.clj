(ns clj-tello.udp
  (:import (java.net InetSocketAddress DatagramPacket DatagramSocket)))

(defn create-socket [port]
  (DatagramSocket. port))
;;lifted from https://github.com/clojure-cookbook/clojure-cookbook/blob/master/05_network-io/5-11_udp.asciidoc

(defn send
  "Send a short textual message over a DatagramSocket to the specified
  host and port. If the string is over 512 bytes long, it will be
  truncated."
  [^DatagramSocket socket msg host port]
  (let [payload (.getBytes msg)
        length (min (alength payload) 512)
        address (InetSocketAddress. host port)
        packet (DatagramPacket. payload length address)]
    (.send socket packet)))

(defn receive
  "Block until a UDP message is received on the given DatagramSocket, and
  return the payload message as a string."
  [^DatagramSocket socket]
  (let [buffer (byte-array 512)
        packet (DatagramPacket. buffer 512)]
    (.receive socket packet)
    (String. (.getData packet)
             0 (.getLength packet))))

(defn receive-loop
  "Given a function and DatagramSocket, will (in another thread) wait
  for the socket to receive a message, and whenever it does, will call
  the provided function on the incoming message."
  [socket f]
  (future (while true (f (receive socket)))
          (recur)))

# clj-tello

My experiments driving the Ryze Tello from my Clojure.  

## Installation

Clone this repo and play at the repl

## Usage

From a clojure Repl:
```
(mount/start)   
(command "takeoff")
(command "ccw 360")
@tello-state  
(command "land")
(mount/stop)
```
### Docs
The commands are documented in the [Tello sdk docs](https://dl-cdn.ryzerobotics.com/downloads/tello/20180910/Tello%20SDK%20Documentation%20EN_1.3.pdf)

### Credits --
UDP connectivity lifted from [Clojure Cookbook](https://github.com/clojure-cookbook/clojure-cookbook/blob/master/05_network-io/5-11_udp.asciidoc)

The Tello Drone was a part of the IBM Drone drop..  Thanks IBM!

## Todo:
Video integration -- The tello is sending the stream via UDP, but this code doesn't do anything with it.
I suspect that integration with other CLJ-drone projects may be very easy.

## License

Copyright © 2019 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

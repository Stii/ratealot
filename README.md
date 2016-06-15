# ratealot

A ratings and reviews API built in Clojure

## Usage

### Install Lein, clojure and dependencies

It is fairly easy and simple to install:

`brew install leiningen`

### Run the application locally

Install redis and start it:

`brew install redis`

then

`redis-server`

Clone this repo and run:

`lein ring server`

*NOTE* It will take a long time the first time since it will download and
install all dependencies (including clojure!)

If it starts up successfully, it should open a browser window to `localhost:3000`. 
If not, just go there.

It will use the defaults for a redis server on the localhost out the box.

If you go straight to `http://localhost:3000` it should open the swagger api docs.

Play with it :) and have fun!

If there is any issues, fix it and create a PR or just add an issue in github.

### Packaging and running as standalone jar

```
lein do clean, ring uberjar
java -jar target/server.jar
```

### Packaging as war

`lein ring uberwar`


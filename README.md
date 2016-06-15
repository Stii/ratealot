# ratealot

A ratings and reviews API built in Clojure

## Usage

### Install Lein, clojure and dependencies

It is fairly easy and simple to install:

`brew install leiningen`

### Run the application locally

Clone this repo and run:

`lein ring server`

*NOTE* It will take a long time the first time since it will download and
install all dependencies (including clojure!)

If it starts up successfully, it should open a browser window to `localhost:3000`. 
If not, just go there.

### Packaging and running as standalone jar

```
lein do clean, ring uberjar
java -jar target/server.jar
```

### Packaging as war

`lein ring uberwar`


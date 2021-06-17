# FOO

- [FOO](#foo)
  - [Build](#build)
  - [Start/Stop](#startstop)

## Build

    ./mvnw clean compile package -Prelease -Dmaven.test.skip=true

## Start/Stop

    ./bin/hello-world-daemon.sh start server
    ./bin/hello-world-daemon.sh start client

    ./bin/hello-world-daemon.sh stop server
    ./bin/hello-world-daemon.sh stop client

# FOO

- [FOO](#foo)
  - [Build](#build)
  - [Start/Stop](#startstop)

## Build

    ./mvnw clean compile package -Prelease -Dmaven.test.skip=true

## Start/Stop

    ./bin/foo-daemon.sh start master-server
    ./bin/foo-daemon.sh start worker-server

    ./bin/foo-daemon.sh stop master-server
    ./bin/foo-daemon.sh stop worker-server

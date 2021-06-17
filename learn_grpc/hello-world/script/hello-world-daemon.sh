#!/bin/sh

usage="Usage: hello-world-daemon.sh (start|stop) <command> "

# if no args specified, show usage
if [ $# -le 1 ]; then
    echo $usage
    exit 1
fi

startStop=$1
shift
command=$1
shift

echo "Begin $startStop $command......"

BIN_DIR=`dirname $0`
BIN_DIR=`cd "$BIN_DIR"; pwd`
HELLO_WORLD_HOME=$BIN_DIR/..

source /etc/profile

export JAVA_HOME=$JAVA_HOME
export HOSTNAME=`hostname`

export HELLO_WORLD_PID_DIR=$HELLO_WORLD_HOME/pid
export HELLO_WORLD_LOG_DIR=$HELLO_WORLD_HOME/logs
export HELLO_WORLD_CONF_DIR=$HELLO_WORLD_HOME/conf
export HELLO_WORLD_LIB_JARS=$HELLO_WORLD_HOME/lib/*

export STOP_TIMEOUT=5

if [ ! -d "$HELLO_WORLD_LOG_DIR" ]; then
    mkdir $HELLO_WORLD_LOG_DIR
fi

log=$HELLO_WORLD_LOG_DIR/hello-world-$command-$HOSTNAME.out
pid=$HELLO_WORLD_PID_DIR/hello-world-$command.pid

cd $HELLO_WORLD_HOME

if [ "$command" = "client" ]; then
    HEAP_INITIAL_SIZE=4g
    HEAP_MAX_SIZE=4g
    HEAP_NEW_GENERATION__SIZE=2g
    LOG_FILE="-Dlogging.config=classpath:log4j2-client.yml"
    CLASS=org.example.hello.world.client.Client
elif [ "$command" = "server" ]; then
    HEAP_INITIAL_SIZE=2g
    HEAP_MAX_SIZE=2g
    HEAP_NEW_GENERATION__SIZE=1g
    LOG_FILE="-Dlogging.config=classpath:log4j2-server.yml"
    CLASS=org.example.hello.world.server.Server
else
    echo "Error: No command named \`$command' was found."
    exit 1
fi

export HELLO_WORLD_OPTS="-server -Xms$HEAP_INITIAL_SIZE -Xmx$HEAP_MAX_SIZE -Xmn$HEAP_NEW_GENERATION__SIZE -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m  -Xss512k -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:LargePageSizeInBytes=128m -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:+PrintGCDetails -Xloggc:gc.log -XX:+HeapDumpOnOutOfMemoryError  -XX:HeapDumpPath=dump.hprof"

case $startStop in
    (start)
        [ -w "$HELLO_WORLD_PID_DIR" ] || mkdir -p "$HELLO_WORLD_PID_DIR"

        if [ -f $pid ]; then
            if kill -0 `cat $pid` > /dev/null 2>&1; then
                echo $command running as process `cat $pid`.  Stop it first.
                exit 1
            fi
        fi

        echo starting $command, logging to $log

        exec_command="$LOG_FILE $HELLO_WORLD_OPTS -classpath $HELLOW_WORLD_CONF_DIR:$HELLO_WORLD_LIB_JARS $CLASS"

        echo "nohup $JAVA_HOME/bin/java $exec_command > $log 2>&1 &"
        nohup $JAVA_HOME/bin/java $exec_command > $log 2>&1 &
        echo $! > $pid
        ;;

    (stop)

        if [ -f $pid ]; then
            TARGET_PID=`cat $pid`
            if kill -0 $TARGET_PID > /dev/null 2>&1; then
                echo stopping $command
                kill $TARGET_PID
                sleep $STOP_TIMEOUT
                if kill -0 $TARGET_PID > /dev/null 2>&1; then
                    echo "$command did not stop gracefully after $STOP_TIMEOUT seconds: killing with kill -9"
                    kill -9 $TARGET_PID
                fi
            else
                echo no $command to stop
            fi
            rm -f $pid
        else
            echo no $command to stop
        fi
        ;;

    (*)
        echo $usage
        exit 1
        ;;

esac

echo "End $startStop $command."

#!/bin/sh

usage="Usage: foo-daemon.sh (start|stop) <command> "

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
FOO_HOME=$BIN_DIR/..

source /etc/profile

export JAVA_HOME=$JAVA_HOME
export HOSTNAME=`hostname`

export FOO_PID_DIR=$FOO_HOME/pid
export FOO_LOG_DIR=$FOO_HOME/logs
export FOO_CONF_DIR=$FOO_HOME/conf
export FOO_LIB_JARS=$FOO_HOME/lib/*

export STOP_TIMEOUT=5

if [ ! -d "$FOO_LOG_DIR" ]; then
    mkdir $FOO_LOG_DIR
fi

log=$FOO_LOG_DIR/foo-$command-$HOSTNAME.out
pid=$FOO_PID_DIR/foo-$command.pid

cd $FOO_HOME

if [ "$command" = "master-server" ]; then
    HEAP_INITIAL_SIZE=4g
    HEAP_MAX_SIZE=4g
    HEAP_NEW_GENERATION__SIZE=2g
    LOG_FILE="-Dlogging.config=classpath:log4j2-master.yml"
    CLASS=org.example.foo.server.master.MasterServer
elif [ "$command" = "worker-server" ]; then
    HEAP_INITIAL_SIZE=2g
    HEAP_MAX_SIZE=2g
    HEAP_NEW_GENERATION__SIZE=1g
    LOG_FILE="-Dlogging.config=classpath:log4j2-worker.yml"
    CLASS=org.example.foo.server.worker.WorkerServer
else
    echo "Error: No command named \`$command' was found."
    exit 1
fi

export FOO_OPTS="-server -Xms$HEAP_INITIAL_SIZE -Xmx$HEAP_MAX_SIZE -Xmn$HEAP_NEW_GENERATION__SIZE -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m  -Xss512k -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:LargePageSizeInBytes=128m -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:+PrintGCDetails -Xloggc:gc.log -XX:+HeapDumpOnOutOfMemoryError  -XX:HeapDumpPath=dump.hprof"

case $startStop in
    (start)
        [ -w "$FOO_PID_DIR" ] || mkdir -p "$FOO_PID_DIR"

        if [ -f $pid ]; then
            if kill -0 `cat $pid` > /dev/null 2>&1; then
                echo $command running as process `cat $pid`.  Stop it first.
                exit 1
            fi
        fi

        echo starting $command, logging to $log

        exec_command="$LOG_FILE $FOO_OPTS -classpath $FOO_CONF_DIR:$FOO_LIB_JARS $CLASS"

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

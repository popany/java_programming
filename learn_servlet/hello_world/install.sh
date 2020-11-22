#!/usr/bin/bash

pushd .. >/dev/null 2>&1
./install_tomcat.sh
popd >/dev/null 2>&1

SERVLET_API_JAR_PATH=../apache-tomcat-8.5.60/lib/servlet-api.jar
HELLOWORLD_CLASS_DIR=../apache-tomcat-8.5.60/webapps/ROOT/WEB-INF/classes/org/example/learn_servlet/hello_world/
WEB_XML_PATH=../apache-tomcat-8.5.60/webapps/ROOT/WEB-INF/web.xml
WEB_XML_CONTENT_TO_INSERT=./web.xml

javac -cp $SERVLET_API_JAR_PATH HelloWorld.java

mkdir -p $HELLOWORLD_CLASS_DIR
cp HelloWorld.class $HELLOWORLD_CLASS_DIR

if [[ -z $(grep "HelloWorld" $WEB_XML_PATH) ]]; then
    sed '/^<\/web-app>/i MARKER' $WEB_XML_PATH | sed -e '/MARKER/r '"$WEB_XML_CONTENT_TO_INSERT" -e '/MARKER/d' > "$WEB_XML_PATH".tmp
    mv "$WEB_XML_PATH".tmp "$WEB_XML_PATH"
fi

#!/usr/bin/bash

pushd .. >/dev/null 2>&1
./install_tomcat.sh
popd >/dev/null 2>&1

SERVLET_API_JAR_PATH=../apache-tomcat-8.5.60/lib/servlet-api.jar
READPARAMS_CLASS_DIR=../apache-tomcat-8.5.60/webapps/TestTomcat/WEB-INF/classes/org/example/learn_servlet/read_params/
WEB_XML_PATH=../apache-tomcat-8.5.60/webapps/TestTomcat/WEB-INF/web.xml
READPARAMS_HTML_PATH=../apache-tomcat-8.5.60/webapps/TestTomcat/read_params.html

javac -cp $SERVLET_API_JAR_PATH ReadParams.java

mkdir -p $READPARAMS_CLASS_DIR
cp ReadParams.class $READPARAMS_CLASS_DIR
cp ./web.xml $WEB_XML_PATH
cp ./read_params.html $READPARAMS_HTML_PATH

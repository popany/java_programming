#!/usr/bin/bash

pushd .. >/dev/null 2>&1
./install_tomcat.sh
popd >/dev/null 2>&1

SERVLET_API_JAR_PATH=../apache-tomcat-8.5.60/lib/servlet-api.jar
CHECKBOX_CLASS_DIR=../apache-tomcat-8.5.60/webapps/TestTomcat/WEB-INF/classes/org/example/learn_servlet/check_box/
WEB_XML_PATH=../apache-tomcat-8.5.60/webapps/TestTomcat/WEB-INF/web.xml
CHECKBOX_HTML_PATH=../apache-tomcat-8.5.60/webapps/TestTomcat/check_box.html

javac -cp $SERVLET_API_JAR_PATH CheckBox.java

mkdir -p $CHECKBOX_CLASS_DIR
cp CheckBox.class $CHECKBOX_CLASS_DIR
cp ./web.xml $WEB_XML_PATH
cp ./check_box.html $CHECKBOX_HTML_PATH

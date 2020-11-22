#!/usr/bin/bash

pushd .. >/dev/null 2>&1
./install_tomcat.sh
popd >/dev/null 2>&1

SERVLET_API_JAR_PATH=../apache-tomcat-8.5.60/lib/servlet-api.jar
HELLOFORM_CLASS_DIR=../apache-tomcat-8.5.60/webapps/TestTomcat/WEB-INF/classes/org/example/learn_servlet/hello_form/
WEB_XML_PATH=../apache-tomcat-8.5.60/webapps/TestTomcat/WEB-INF/web.xml
HELLO_GET_HTML_PATH=../apache-tomcat-8.5.60/webapps/TestTomcat/hello_get.html
HELLO_POST_HTML_PATH=../apache-tomcat-8.5.60/webapps/TestTomcat/hello_post.html

javac -cp $SERVLET_API_JAR_PATH HelloForm.java

mkdir -p $HELLOFORM_CLASS_DIR
cp HelloForm.class $HELLOFORM_CLASS_DIR
cp ./web.xml $WEB_XML_PATH
cp ./hello_get.html $HELLO_GET_HTML_PATH
cp ./hello_post.html $HELLO_POST_HTML_PATH

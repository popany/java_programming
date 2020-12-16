#!/usr/bin/bash

TOMCAT_TAR=apache-tomcat-8.5.60.tar.gz
TOMCAT_DIR=apache-tomcat-8.5.60
TOMCAT_TAR_URL=https://archive.apache.org/dist/tomcat/tomcat-8/v8.5.60/bin/apache-tomcat-8.5.60.tar.gz

install_tomcat() {
    if [[ ! -f "$TOMCAT_TAR" ]]; then
        wget "$TOMCAT_TAR_URL"
    fi

    tar -xvzf "$TOMCAT_TAR"
}

if [[ ! -d "$TOMCAT_DIR" ]]; then
    install_tomcat
fi

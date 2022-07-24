#!/bin/sh

# enter plugin directory
cd ./bankapi || exit

# compile project
mvn compile
# build package
mvn clean package
cd ..

# make directory for plugins if it doesn't exist
mkdir -p ./server/plugins

# move target jar into plugin folder
mv ./bankapi/target/BankAPI-1.0.jar ./server/plugins/
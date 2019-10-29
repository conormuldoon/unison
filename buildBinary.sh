#!/bin/sh
cd front-end
yarn install
yarn build
rm -r ../back-end/src/main/resources/public/unison/*
cp -r build/* ../back-end/src/main/resources/public/unison/
cd ../back-end
./mvnw clean compile package
# The argument to the script is the version number.
cp target/unison-server-*-SNAPSHOT.jar target/unison-$1.jar

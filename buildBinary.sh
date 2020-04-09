#!/bin/sh
sed -i "s/[0-9]\.[0-9]\.[0-9]/$1/" README.md
cd front-end
sed -i "s/version\": \"[0-9]\.[0-9]\.[0-9]/version\": \"$1/" package.json
yarn install
yarn build
rm -r ../back-end/src/main/resources/public/*
cp -r build/* ../back-end/src/main/resources/public
cd ../back-end
sed -i "s/[0-9]\.[0-9]\.[0-9]-SNAPSHOT/$1-SNAPSHOT/" pom.xml
./mvnw clean compile package
# The argument to the script is the version number.
cp target/unison-server-*-SNAPSHOT.jar target/unison-$1.jar
./generateDocs $1

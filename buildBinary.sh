#!/bin/sh
# The argument to the script is the release version number.
if [ -z "$1" ]
then
    echo "Please specify the release version number."
    exit 2
fi
sed -i "s/[0-9]*\.[0-9]*\.[0-9]*.*\.jar/$1\.jar/" README.md
cd front-end
sed -i "s/version\": \"[0-9]*\.[0-9]*\.[0-9]*.*\"/version\": \"$1\"/" package.json
yarn install
yarn build
rm -r ../back-end/src/main/resources/public/*
cp -r build/* ../back-end/src/main/resources/public
cd ../back-end
sed -i "s/[0-9]*\.[0-9]*\.[0-9]*-SNAPSHOT/$1/" pom.xml
./mvnw clean compile package
cp target/unison-server-$1.jar target/unison-$1.jar
cd ..
./generateDocs.sh

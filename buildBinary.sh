#!/bin/sh
# The argument to the script is the release version number.
if [ -z "$1" ]
then
    echo "Please specify the release version number."
    exit 2
fi
sed -i "s/[0-9]*\.[0-9]*\.[0-9]*.*\.jar/$1\.jar/" README.md
mkdir back-end/src/main/resources/heTemp
cp -r back-end/src/main/resources/public/* back-end/src/main/resources/heTemp/
cd front-end
sed -i "s/version\": \"[0-9]*\.[0-9]*\.[0-9]*.*\"/version\": \"$1\"/" package.json
yarn install
yarn build
cp -r build/* ../back-end/src/main/resources/public
cd ../back-end
sed -i "s/[0-9]*\.[0-9]*\.[0-9]*-SNAPSHOT/$1/" pom.xml
./mvnw clean compile package
rm -r src/main/resources/public/*
cp -r src/main/resources/heTemp/* src/main/resources/public/
rm -r src/main/resources/heTemp
cp target/unison-server-$1.jar target/unison-$1.jar
cd ..

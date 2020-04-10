#!/bin/sh
# The first argument to the script is the release version number and the second argument is the next version number..
sed -i "s/[0-9]\.[0-9]\.[0-9]/$2/" README.md
cd front-end
sed -i "s/version\": \"[0-9]\.[0-9]\.[0-9].*\"/version\": \"$1\"/" package.json
yarn install
yarn build
rm -r ../back-end/src/main/resources/public/*
cp -r build/* ../back-end/src/main/resources/public
cd ../back-end
sed -i "s/[0-9]\.[0-9]\.[0-9]-SNAPSHOT/$1/" pom.xml
./mvnw clean compile package
cp target/unison-server-$1.jar target/unison-$1.jar
cd ..
./generateDocs.sh
cd front-end
sed -i "s/version\": \"[0-9]\.[0-9]\.[0-9]/version\": \"$2-alpha/" package.json
cd ../back-end
sed -i "0,/<version>[0-9]\.[0-9]\.[0-9]/{s/<version>[0-9]\.[0-9]\.[0-9]/<version>$2-SNAPSHOT/}" pom.xml # This requires GNU sed. GNU sed can be installed on OS X using homebrew (brew install gnu-sed).


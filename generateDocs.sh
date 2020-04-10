#!/bin/sh
rm -rf docs/
mkdir -p docs/back-end
cd back-end
mvn javadoc:javadoc
mv target/site/apidocs/* ../docs/back-end
cd ../front-end
mkdir ../docs/front-end
yarn docs
mv docs/* ../docs/front-end

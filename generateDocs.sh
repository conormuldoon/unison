#!/bin/sh
rm -r docs/back-end/*
rm -r docs/front-end/*
cd back-end
mvn javadoc:javadoc
mv target/site/apidocs/* ../docs/back-end
cd ../front-end
yarn docs
mv docs/* ../docs/front-end

#!/bin/sh
# A script executed by the Docker container that checks whether the harmonie database is ready prior to launching the Spring Boot application.
set -e

until PGPASSWORD=$POSTGRES_PASSWORD psql -h $1 -U "$POSTGRES_USER" -c '\q'; do
  >&2 echo "Harmonie database is not ready - sleeping."
  sleep 5
done
  
echo "Harmonie database is ready to receive connections."
exec java -cp app:app/lib/* -Ddefault.username=unisonuser -Ddefault.encoded=$2a$10$A2tDbzPnFa0ti2z5Da/HNeSlxVL2MC8fLK1AWXbphOI1Vo6S8swGC eu.acclimatize.unison.UnisonServerApplication



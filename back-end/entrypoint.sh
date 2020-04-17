#!/bin/sh
# A script executed by the Docker container that checks whether the harmonie database is ready prior to launching the Spring Boot application.
set -e

until PGPASSWORD=$POSTGRES_PASSWORD psql -h $UNISON_DB_HOST -U $POSTGRES_USER -c '\q'; do
  >&2 echo "Harmonie database is not ready - sleeping."
  sleep 5
done
  
echo "Harmonie database is ready to receive connections."

exec java -cp app:app/lib/* -Ddefault.username=$UNISON_USER -Ddefault.encoded=$UNISON_ENCODED eu.acclimatize.unison.UnisonServerApplication




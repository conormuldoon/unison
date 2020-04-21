# Unison
Unison enables (HARMONIE-AROME) numerical weather forecast data from meteorological services to be tracked and visualised through a web interface and historical data to be accessed via a RESTful API. The version of the code in this repository (deployed at https://aqua.ucd.ie/unison/) has been configured for use with Met Éireann's model (coverage of Ireland and the UK), but Unison can also be used with the Norwegian Meteorological Institute's API or other APIs that conform to the same schema. The service has been developed using Spring Boot, JPA, and React and can be configured for use with either an embedded GeoDB geospatial database or Postgres/PostGIS for a production environment.

## Documentation
See the [Javadoc](https://conormuldoon.github.io/unison/docs/back-end/) for a description of the Java classes for the back-end or the [JSDoc](https://conormuldoon.github.io/unison/docs/front-end/) for an overview of the React components. The documentation for the RESTful API is available [here](https://documenter.getpostman.com/view/3155829/SVtWvmRS) with examples for the endpoints used by https://aqua.ucd.ie/unison.

## Running

### Binary
Unison requires Java 11 to operate. Download the jar file from the [latest release](https://github.com/conormuldoon/unison/releases/latest/) and run it using the `-jar` option. Once the application has started, enter an initial user name and password in the terminal, which will be required for adding/removing the locations to track, and then open `http://localhost:8080` in the browser. To change the port, override the `server.port` property using a Java system property or the `--server.port` argument for main. The initial user credentials can also be specified using (`default.username` and `default.encoded`) properties as an alternative to using the console where the password is encoded using a bcrypt encoder. The binary release is configured for use with GeoDB.

### From source

#### Start the back-end

```
cd back-end
./mvnw clean compile spring-boot:run
```
Wait until the application has started and if not using the `default.username` and `default.encoded` properties, then enter an initial user name and password, which will be required for adding/removing the locations to track.

#### Start the front-end

```
cd front-end
```
If not previously run: `yarn install`
```
yarn start
```

### Docker

Containers for the system that use Postgres/PostGIS and Nginx can be created using Docker Compose.
```
docker-compose up
```
Once running, open `http://localhost:4545` to access the front-end. In the Docker configuration, the default user name is `unisonuser` and the default password is `unisonpassword`.

## Adding additional users

If using GeoDB (Unison is configured for GeoDB in this repository), stop the server if it is in use.
```
cd back-end
```
If not previously packaged: `./mvnw clean compile package`
```
java -cp target/unison-server-0.6.1-SNAPSHOT.jar -Dloader.main=eu.acclimatize.unison.user.UserConsole org.springframework.boot.loader.PropertiesLauncher
```

Enter the user credentials (if the user name already exists, the password will be updated).

Alternatively, the API can be used to add users or update passwords.

## Testing

### Back-end 
```
cd back-end
./mvnw clean compile test
```
To generate a coverage report:
```
./mvnw verify
```
The coverage report will be located in the `back-end/target/site/jacoco` directory.

### Front-end
```
cd front-end
```
If not previously run: `yarn install`
```
yarn test
```
For coverage, watch needs to be disabled (see https://github.com/facebook/create-react-app/issues/6888).
```
yarn test --coverage --watchAll=false
```
The coverage report will be located in the `front-end/coverage/locv-report` directory.

## Switching between GeoDB and Postgres/PostGIS

With the Postgres/PostGIS configuration, the schema is not created automatically and it should be created using psql and the `back-end/src/main/resources/pg_pgis_schema.sql` schema file.

The database configuration is specified in the `back-end/src/main/resources/application.properties` file. Comment out the lines related GeoDB and uncomment the lines related to Postgres/PostGIS or vice versa and restart the server. Alternatively, to automate the process (for instance, for continuous deployment purposes), use the Python script to comment out and uncomment the relevant lines:
```
cd back-end/src/main/python
python switch_db_config.py
```

It is recommended that the `spring.datasource.password` property be changed to a new password (a system property or the `back-end/src/main/python/change_db_password.py` Python script can be used for automation purposes). 

## Changing the model

To change the model, edit the (HARMONIE-AROME) API URI `api.uri` and the associated time zone `api.timezone` properties in the `back-end/src/main/resources/application.properties` file and restart the server. Alternatively, if running a packaged version of the application, override the `api.uri` and `api.timezone` properties using Java system properties or the `--api.uri` and `--api.timezone` arguments for main.

## Acknowlegements
Unison has been developed as part of the Acclimatize Project (https://www.acclimatize.eu/), which is partly funded under the EU Ireland Wales European Territorial Co-operation (ETC) programme.

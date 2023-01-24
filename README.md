# Unison
Unison enables (HARMONIE-AROME) numerical weather forecast data from meteorological services to be tracked and visualised through a web interface and historical data to be accessed via an API. The version of the code in this repository (deployed at https://aqua.ucd.ie/unison/) has been configured for use with Met Éireann's model (coverage of Ireland and the UK), but Unison can also be used with the Norwegian Meteorological Institute's endpoint or other endpoints that conform to the same schema. The service has been developed using Spring Boot, JPA, React, and TypeScript and can be configured for use with either an embedded H2/GeoDB geospatial database or Postgres/PostGIS for a production environment.

## Documentation
See the [Javadoc](https://conormuldoon.github.io/unison/docs/back-end/) for a description of the Java classes for the back-end or the [TypeDoc](https://conormuldoon.github.io/unison/docs/front-end/modules.html) for an overview of the React components. The Unison API is based on HATEOAS and the Hypertext Application Language (HAL) and the https://aqua.ucd.ie/unison/ deployment of the API can be explored [here](https://aqua.ucd.ie/unison/explorer#hkey0=Accept&hval0=application/hal+json&uri=https://aqua.ucd.ie/unison/). Some examples of how the endpoints are used can be viewed [here](https://documenter.getpostman.com/view/3155829/SVtWvmRS). It is recommended that the HAL representational model be used to obtain the links so that updates to the API will be reflected in the client.

## Running

### Binary
Download the jar file from the [latest release](https://github.com/conormuldoon/unison/releases/latest/) and run it using the `-jar` option. Once the application has started, enter an initial user name and password in the terminal, which will be required for adding/removing the locations to track, and then open `http://localhost:8080` in the browser. To change the port, override the `server.port` property using a Java system property or the `--server.port` argument for main. The initial user credentials can also be specified using (`default.username` and `default.encoded`) properties as an alternative to using the console where the password is encoded using a bcrypt encoder. The binary release is configured for use with GeoDB.

### From source

#### Compile and start the back-end

```
cd back-end
./mvnw clean compile spring-boot:run
```
Wait until the application has started and if not using the `default.username` and `default.encoded` properties, then enter an initial user name and password, which will be required for adding/removing the locations to track.

#### Start the front-end

The current version of Unison uses Yarn Zero-Installs, so there is no need to run `yarn install`.
```
cd front-end
yarn
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
java -cp target/unison-server-0.9.2.jar -Dloader.main=eu.acclimatize.unison.user.UserConsole org.springframework.boot.loader.PropertiesLauncher
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
yarn test
```
For existing coverage of all files, watch needs to be disabled (see https://github.com/facebook/create-react-app/issues/6888).
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

To change the model, edit the (HARMONIE-AROME) endpoint URI `harmonie.uri` and the associated time zone `harmonie.timezone` properties in the `back-end/src/main/resources/application.properties` file and restart the server. Alternatively, if running a packaged version of the application, override the `harmonie.uri` and `harmonie.timezone` properties using Java system properties or the `--harmonie.uri` and `--harmonie.timezone` arguments for main.

## Acknowledgement
Unison has been developed as part of the Acclimatize Project (https://www.acclimatize.eu/). The Acclimatize project is part funded by the European Regional Development Fund through the Ireland Wales Cooperation programme.

![Acclimatize logo](https://raw.githubusercontent.com/conormuldoon/unison/master/front-end/src/Acclimatize-Logo.png) 

![Partners logo](https://github.com/conormuldoon/unison/blob/master/front-end/src/partners-logos.jpg?raw=true)

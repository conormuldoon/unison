# Unison
Unsion is a service that enables (HARMONIE-AROME) numerical forecast data from meteorological services to be tracked and visualised through a web interface and historical data to be accessed via an API. The initial deployment of the service (see http://aqua.ucd.ie/unison/) was designed for use with Met Éireann's model (coverage of Ireland and the UK), but the version of the code here has been configured for use with the Norwegian Meteorological Institute's model, as it is openly accessible (does not require IP address whitelisting). Unison has been developed using Spring Boot, JPA, and React and can be configured for use with either an embedded GeoDB geospatial database or Postgres/PostGIS for a production environment. For more detailed information, see the [Javadoc](https://conormuldoon.github.io/unison/docs/back-end/) for the back-end or the [Styleguidist](https://conormuldoon.github.io/unison/docs/front-end/) for the React components.

## Running

### Start the back-end

```
cd back-end
mvn clean compile spring-boot:run
```
Wait until the application has started and then enter an initial user name and password, which will be required for adding/removing the locations at which data will be recorded.

### Start the front-end

```
cd front-end
yarn start
```
## Adding additional users

```
cd back-end
```
If not previously compiled: `mvn clean compile package`
```
java -cp target/unison-server-0.0.1-SNAPSHOT.jar -Dloader.main=eu.acclimatize.unison.user.UserConsole org.springframework.boot.loader.PropertiesLauncher
```

Enter the user credentials (if the user name already exists, the password will be upated).
## Testing

### Back-end
```
cd back-end
mvn clean compile test
```
To generate a coverage report:
```
mvn verify
```
The coverage report will be located in the target/site/jacoco directory.

### Front-end
```
cd front-end
yarn test
```
For coverage, watch needs to be disabled (see https://github.com/facebook/create-react-app/issues/6888).
```
yarn test --coverage --watchAll=false
```
The coverage report will be located in the coverage/locv-report directory.

## Acknowlegements
Unison has been developed as part of the Acclimatize Project (https://www.acclimatize.eu/), which is partly funded under the EU Ireland Wales European Territorial Co-operation (ETC) programme.

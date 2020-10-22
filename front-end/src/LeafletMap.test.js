import React from 'react';
import ReactDOM from 'react-dom';
import LeafletMap from './LeafletMap';

import { render, fireEvent } from "@testing-library/react";
import fetchMock from 'fetch-mock';


const location = {
  "name": "UCD",
  "_links": {
    "self": {
      "href": "http://localhost:8080/locationCollection/UCD"
    },
    "cloudiness": {
      "href": "http://localhost:8080/locationCollection/UCD/cloudiness{?fromDate,toDate}",
      "templated": true
    },
    "cloudLevel": {
      "href": "http://localhost:8080/locationCollection/UCD/cloudLevel{?fromDate,toDate}",
      "templated": true
    },
    "dewPoint": {
      "href": "http://localhost:8080/locationCollection/UCD/dewPoint{?fromDate,toDate}",
      "templated": true
    },
    "humidity": {
      "href": "http://localhost:8080/locationCollection/UCD/humidity{?fromDate,toDate}",
      "templated": true
    },
    "precipitation": {
      "href": "http://localhost:8080/locationCollection/UCD/precipitation{?fromDate,toDate}",
      "templated": true
    },
    "pressure": {
      "href": "http://localhost:8080/locationCollection/UCD/pressure{?fromDate,toDate}",
      "templated": true
    },
    "temperature": {
      "href": "http://localhost:8080/locationCollection/UCD/temperature{?fromDate,toDate}",
      "templated": true
    },
    "windDirection": {
      "href": "http://localhost:8080/locationCollection/UCD/windDirection{?fromDate,toDate}",
      "templated": true
    },
    "windSpeed": {
      "href": "http://localhost:8080/locationCollection/UCD/windSpeed{?fromDate,toDate}",
      "templated": true
    }
  }
}


it('renders without crashing', async () => {

  const div = document.createElement('div');

  ReactDOM.render(<LeafletMap location={location} mapCentre={[59.922326, 10.751560]} />, div);
  ReactDOM.unmountComponentAtNode(div);
});

it('mathes snapshot', () => {


  const { container } = render(<LeafletMap linksProperty={location} mapCentre={[59.922326, 10.751560]} />);

  expect(container).toMatchSnapshot();

});

it('displays popup', () => {


  // position: [lat, lon]
  const marker = [{ name: location.name, position: [53.308441, -6.223682] }];

  const curVar = 'Temperature';
  const fromDate = '1/2/2018';
  const toDate = '7/10/2019';
  const mapCentre = [53.35014, -6.266155];

  fetchMock.get('end:/temperature?fromDate=' + fromDate + '&toDate=' + toDate, []);

  const markerCallback = jest.fn();

  const featureProperties = new Map();
  featureProperties.set(location.name, location);
  const { getAllByAltText } = render(<LeafletMap linksProperty={location} featureProperties={featureProperties}
    curVar={curVar} mapCentre={mapCentre} markerCallback={markerCallback} marker={marker} fromDate={fromDate} toDate={toDate} />);

  // Firing click event for marker icon image
  fireEvent.click(getAllByAltText('')[1]);
  expect(markerCallback).toHaveBeenCalledWith(location.name);

  fetchMock.restore();

});

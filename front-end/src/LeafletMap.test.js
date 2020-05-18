import React from 'react';
import ReactDOM from 'react-dom';
import LeafletMap from './LeafletMap';

import { render, fireEvent } from "react-testing-library";
import fetchMock from 'fetch-mock';


const location = {
  name: "UCD",
  links: {
    cloudiness: "/location/UCD/cloudiness{?fromDate,toDate}",
    cloudLevel: "/location/UCD/cloudLevel{?fromDate,toDate}",
    dewPoint: "/location/UCD/dewPoint{?fromDate,toDate}",
    humidity: "/location/UCD/humidity{?fromDate,toDate}",
    precipitation: "/location/UCD/precipitation{?fromDate,toDate}",
    pressure: "/location/UCD/pressure{?fromDate,toDate}",
    temperature: "/location/UCD/temperature{?fromDate,toDate}",
    windDirection: "/location/UCD/windDirection{?fromDate,toDate}",
    windSpeed: "/location/UCD/windSpeed{?fromDate,toDate}",
    harvest: "/location/UCD/harvest"
  }
};

it('renders without crashing', async () => {

  const div = document.createElement('div');

  ReactDOM.render(<LeafletMap location={location} mapCentre={[59.922326, 10.751560]} />, div);
  ReactDOM.unmountComponentAtNode(div);
});

it('mathes snapshot', () => {


  const { container } = render(<LeafletMap location={location} mapCentre={[59.922326, 10.751560]} />);

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
  const { getAllByAltText } = render(<LeafletMap location={location} featureProperties={featureProperties}
    curVar={curVar} mapCentre={mapCentre} markerCallback={markerCallback} marker={marker} fromDate={fromDate} toDate={toDate} />);

  // Firing click event for marker icon image
  fireEvent.click(getAllByAltText('')[1]);
  expect(markerCallback).toHaveBeenCalledWith(location.name);

  fetchMock.restore();

});

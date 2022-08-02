import { enableFetchMocks } from 'jest-fetch-mock';
enableFetchMocks();

import LeafletMap, { MapMarkerFactory } from './LeafletMap';
import { Marker } from 'react-leaflet';

import { render, fireEvent, screen, act } from "@testing-library/react";
import fetchMock from 'jest-fetch-mock';
import { createPopupFactory } from './ChartPopup';


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

  render(<LeafletMap mapCentre={[59.922326, 10.751560]} marker={[]} markerCallback={() => null} />);
});

it('mathes snapshot', () => {


  const { container } = render(<LeafletMap marker={[]} mapCentre={[59.922326, 10.751560]} markerCallback={() => null} />);

  expect(container).toMatchSnapshot();

});

it('displays popup', async () => {


  // position: [lat, lon]
  const marker: MapMarkerFactory[] = [function (component, callback, image): JSX.Element {
    return <Marker key={location.name} position={[53.308441, -6.223682]} onClick={callback.bind(component, location.name)} icon={image} />
  }];

  const fromDate = '1-2-2018';
  const toDate = '7-10-2019';
  const mapCentre: [number, number] = [53.35014, -6.266155];

  fetchMock.mockResponse("[]");

  const markerCallback = jest.fn();

  const featureProperties = new Map();
  featureProperties.set(location.name, location);

  const popupFactory = createPopupFactory("http://localhost:8080/locationCollection/UCD/temperature?fromDate=" + fromDate + "&toDate=" + toDate,
    "Temperature", location.name);
  render(<LeafletMap
    mapCentre={mapCentre} markerCallback={markerCallback} marker={marker} popupFactory={popupFactory} />);

  // Firing click event for marker icon image
  const arr = await screen.findAllByAltText('');

  await act(() => fireEvent.click(arr[1]));

  await screen.findByRole('button', { name: 'email' });
  expect(markerCallback).toHaveBeenCalledWith(location.name);


});

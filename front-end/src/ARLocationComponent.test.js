import "@testing-library/jest-dom/extend-expect";
import React from 'react';
import ReactDOM from 'react-dom';
import { fireEvent, render } from "react-testing-library";
import ARLocationComponent from './ARLocationComponent';

it('renders without crashing', async () => {

  const div = document.createElement('div');

  ReactDOM.render(<ARLocationComponent obtainData={() => { }} />, div);
  ReactDOM.unmountComponentAtNode(div);
});


it('mathes snapshot', () => {


  const { container } = render(<ARLocationComponent obtainData={() => { }} />);

  expect(container).toMatchSnapshot();

});

const linksProperty = {
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


it('toggles add location correctly', () => {
  const confirmSpy = jest.spyOn(window, 'confirm');
  confirmSpy.mockImplementation(() => true);

  const location = { name: 'UCD', links: { self: '/location/UCD' } };
  const { getByTestId, getByText } = render(<ARLocationComponent location={location} obtainData={() => { }} linksProperty={linksProperty} />);
  expect(getByTestId('lf-button')).toHaveTextContent('Add Location');
  expect(getByTestId('rm-button')).toHaveTextContent('Remove ' + location.name);
  fireEvent.click(getByText('Add Location'));
  expect(getByTestId('lf-button')).toHaveTextContent('Hide');
  fireEvent.click(getByText('Hide'));
  expect(getByTestId('lf-button')).toHaveTextContent('Add Location');
  fireEvent.click(getByText('Add Location'));
  fireEvent.click(getByText('Remove ' + location.name));
  expect(getByTestId('lf-button')).toHaveTextContent('Add Location');

  confirmSpy.mockClear();

});


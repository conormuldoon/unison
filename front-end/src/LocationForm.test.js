import fetchMock from 'fetch-mock';
import React from 'react';
import ReactDOM from 'react-dom';
import { fireEvent, render } from "react-testing-library";
import { API } from './Constant';
import LocationForm from './LocationForm';
import { locationPutObject } from './Util';
import HttpStatus from 'http-status-codes';


it('renders without crashing', async () => {

  const div = document.createElement('div');

  ReactDOM.render(<LocationForm obtainData={() => { }} hideDisplay={() => { }} toggleDisplay={() => { }} display={true} />, div);
  ReactDOM.unmountComponentAtNode(div);
});


it('mathes snapshot', () => {


  const { container } = render(<LocationForm obtainData={() => { }} hideDisplay={() => { }} toggleDisplay={() => { }} display={true} />);

  expect(container).toMatchSnapshot();

});

function changeValue(getByLabelText, labelText, value) {
  fireEvent.change(getByLabelText(labelText), { target: { value: value } });
}

const collectionModel = {

  "_links": {
    "self": {
      "href": "http://localhost:8080/locationCollection"
    },
    "contains": {
      "href": "http://localhost:8080/locationCollection/contains{?name}",
      "templated": true
    }
  }
}

it('sends the data in the form when submit is clicked', async (done) => {
  jest.spyOn(window, 'fetch').mockImplementation(() => {
    return {
      headers: { get: () => { } }, ok: true,
      json: () => { return { properties: { links: { harvest: '/harvest' } } } }
    }
  });
  fetchMock.get('end:contains?name=UCD', { value: false });
  const hideDisplay = jest.fn();

  const location = 'UCD';

  const lon = '-6.223682';
  const lat = '53.308441';

  const obtainData = () => {


    expect(hideDisplay).toHaveBeenCalledTimes(1);

    expect(fetch).toBeCalledWith('http://localhost:8080/locationCollection', locationPutObject(location, lon, lat));
    done();
  };

  const map = new Map();

  const component = <LocationForm display={true} collectionModel={collectionModel} obtainData={obtainData} hideDisplay={hideDisplay}
    toggleDisplay={() => { }} featureProperties={map} />;
  const { getByText, getByLabelText } = render(component);

  changeValue(getByLabelText, 'Location name:', location);
  changeValue(getByLabelText, 'Longitude:', lon);
  changeValue(getByLabelText, 'Latitude:', lat);

  jest.spyOn(window, 'alert').mockImplementation(() => { });

  fireEvent.click(getByText('Submit'));
  fetchMock.restore();


});

it('handles add location', async (done) => {
  const location = 'UCD';

  fetchMock.get('end:contains?name=UCD', { value: false });
  fetchMock.put('end:/location', { status: HttpStatus.OK, body: { properties: { links: { harvest: '/harvest' } } } });
  fetchMock.post('end:/harvest', { status: HttpStatus.OK });


  jest.spyOn(window, 'alert').mockImplementation(() => { });
  const hideDisplay = jest.fn();


  const obtainData = () => {
    expect(hideDisplay).toHaveBeenCalledTimes(1);
    expect(alert).toBeCalledWith(location + ' was added.');
    done();
  };

  const map = new Map();
  const component = <LocationForm display={true} collectionModel={collectionModel} obtainData={obtainData} hideDisplay={hideDisplay}
    toggleDisplay={() => { }} featureProperties={map} />;
  const { getByText, getByLabelText } = render(component);

  changeValue(getByLabelText, 'Location name:', location);

  fireEvent.click(getByText('Submit'));

  fetchMock.restore();


});


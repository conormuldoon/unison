import fetchMock from 'fetch-mock';
import React from 'react';
import ReactDOM from 'react-dom';
import { fireEvent, render, waitFor } from "@testing-library/react";
import LocationForm from './LocationForm';
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

async function changeValue(getByLabelText, labelText, value) {

  fireEvent.change(await waitFor(() => getByLabelText(labelText)), { target: { value: value } });
}

it('sends the data in the form when submit is clicked', async () => {
  const fetch = jest.spyOn(window, 'fetch').mockImplementation(() => {
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



  };

  const map = new Map();

  const component = <LocationForm display={true} containsRef={"http://localhost:8080/locationCollection/contains{?name}"} selfRef={"http://localhost:8080/locationCollection"} obtainData={obtainData} hideDisplay={hideDisplay}
    toggleDisplay={() => { }} featureProperties={map} />;
  const { getByText, getByLabelText } = render(component);

  await changeValue(getByLabelText, 'Location name:', location);
  await changeValue(getByLabelText, 'Longitude:', lon);
  await changeValue(getByLabelText, 'Latitude:', lat);

  jest.spyOn(window, 'alert').mockImplementation(() => { });
  fetchMock.put('end:locationCollection', 202);
  fetchMock.post('end:locationCollection/UCD', 200);
  fireEvent.click(await waitFor(() => getByText('Submit')));
  await changeValue(getByLabelText, 'Latitude:', '');

  fetchMock.restore();


});

it('handles add location', async () => {
  const location = 'UCD';

  fetchMock.get('end:contains?name='+location, { value: false });
  fetchMock.put('end:/location', { status: HttpStatus.OK, body: { properties: { links: { harvest: '/harvest' } } } });


  jest.spyOn(window, 'alert').mockImplementation(() => { });
  const hideDisplay = jest.fn();


  const obtainData = () => {

    expect(alert).toBeCalledWith(location + ' was added.');

  };

  const map = new Map();
  const component = <LocationForm display={true} containsRef={"http://localhost:8080/locationCollection/contains{?name}"} selfRef={"http://localhost:8080/locationCollection"} obtainData={obtainData} hideDisplay={hideDisplay}
    toggleDisplay={() => { }} featureProperties={map} />;
  const { getByText, getByLabelText } = render(component);

  await changeValue(getByLabelText, 'Location name:', location);

  fetchMock.put('end:locationCollection', 202);
  fetchMock.post('end:locationCollection/'+location, 200);
  const button = await waitFor(() => getByText('Submit'));
  fireEvent.click(button);
  await changeValue(getByLabelText, 'Latitude:', '');
  expect(hideDisplay).toHaveBeenCalledTimes(1);

  fetchMock.restore();


});


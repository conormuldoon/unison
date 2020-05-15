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

it('posts the data in the form when submit is clicked', async (done) => {
  jest.spyOn(window, 'fetch').mockImplementation(() => { return { headers: { get: () => '0' }, ok: true, json: () => 1 } });
  const hideDisplay = jest.fn();

  const location = 'UCD';

  const lon = '-6.223682';
  const lat = '53.308441';



  const obtainData = () => {


    expect(hideDisplay).toHaveBeenCalledTimes(1);

    expect(fetch).toBeCalledWith(API + '/location', locationPutObject(location, lon, lat));
    done();
  };

  const component = <LocationForm display={true} obtainData={obtainData} hideDisplay={hideDisplay} toggleDisplay={() => { }} />;
  const { getByText, getByLabelText } = render(component);

  changeValue(getByLabelText, 'Location name:', location);
  changeValue(getByLabelText, 'Longitude:', lon);
  changeValue(getByLabelText, 'Latitude:', lat);

  jest.spyOn(window, 'alert').mockImplementation(() => { });

  fireEvent.click(getByText('Submit'));


});

it('handles add location', async (done) => {
  fetchMock.put('end:/location', { status: HttpStatus.OK, body: { json: () => { } } });
  fetchMock.post('end:/harvest', { status: HttpStatus.OK });

  jest.spyOn(window, 'alert').mockImplementation(() => { });
  const hideDisplay = jest.fn();

  const location = 'UCD';
  const obtainData = () => {
    expect(hideDisplay).toHaveBeenCalledTimes(1);
    expect(alert).toBeCalledWith(location + ' was added');
    done();
  };

  const component = <LocationForm display={true} obtainData={obtainData} hideDisplay={hideDisplay} toggleDisplay={() => { }} />;
  const { getByText, getByLabelText } = render(component);

  changeValue(getByLabelText, 'Location name:', location);

  fireEvent.click(getByText('Submit'));

  fetchMock.restore();


});


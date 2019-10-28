import fetchMock from 'fetch-mock';
import React from 'react';
import ReactDOM from 'react-dom';
import { fireEvent, render } from "react-testing-library";
import { API } from './Constant';
import LocationForm from './LocationForm';
import { locationPostObject } from './Util';




it('renders without crashing', async () => {

  const div = document.createElement('div');

  ReactDOM.render(<LocationForm obtainData={() => { }} hideDisplay={() => { }} toggleDisplay={() => { }} display={true} />, div);
  ReactDOM.unmountComponentAtNode(div);
});


it('mathes snapshot', () => {


  const { container } = render(<LocationForm obtainData={() => { }} hideDisplay={() => { }} toggleDisplay={() => { }} display={true} />);

  expect(container).toMatchSnapshot();

});


it('posts data in form when submit is clicked', async (done) => {
  jest.spyOn(window, 'fetch').mockImplementation(() => { return { ok: true, json: () => 1 } });
  const hideDisplay = jest.fn();

  const location = 'UCD';
  const username = 'conor';
  const password = 'password';

  const lon = '-6.223682';
  const lat = '53.308441';



  const obtainData = () => {


    expect(hideDisplay).toHaveBeenCalledTimes(1);
    //  expect(fetch).toBeCalledWith(locationPostObject('A','u','p',-6,54));
    expect(fetch).toBeCalledWith(API + '/addLocation', locationPostObject(location, username, password, lon, lat));
    done();
  };

  const component = <LocationForm display={true} obtainData={obtainData} hideDisplay={hideDisplay} toggleDisplay={() => { }} />;
  const { getByText, getByLabelText } = render(component);

  function changeValue(labelText, value) {
    fireEvent.change(getByLabelText(labelText), { target: { value: value } });
  }


  changeValue('Location name:', location);
  changeValue('Longitude:', lon);
  changeValue('Latitude:', lat);
  changeValue('Username:', username);
  changeValue('Password:', password);

  jest.spyOn(window, 'alert').mockImplementation(() => { });
  //fireEvent.keyPress(getByLabelText('Location:'),{ key: 'A', code: 65, charCode: 65 });
  fireEvent.click(getByText('Submit'));


});

it('handles add location', async (done) => {
  fetchMock.post('end:/addLocation', '1');

  jest.spyOn(window, 'alert').mockImplementation(() => { });
  const hideDisplay = jest.fn();

  const obtainData = () => {
    expect(hideDisplay).toHaveBeenCalledTimes(1);
    expect(alert).toBeCalledWith(' added');
    done();
  };

  const component = <LocationForm display={true} obtainData={obtainData} hideDisplay={hideDisplay} toggleDisplay={() => { }} />;
  const { getByText } = render(component);

  fireEvent.click(getByText('Submit'));

  fetchMock.restore();


});


const testAdd = async (done, retVal, message) => {
  fetchMock.post('end:/addLocation', retVal);

  jest.spyOn(window, 'alert').mockImplementation((messStr) => {
    expect(messStr).toEqual(message);
    done();
  });


  const component = <LocationForm display={true} obtainData={() => { }} toggleDisplay={() => { }} hideDisplay={() => { }} />;
  const { getByText } = render(component);

  fireEvent.click(getByText('Submit'));

  fetchMock.restore();
}

it('displays that the location is already present', async (done) => {
  testAdd(done, '0', 'Location already exists');

});


it('Invalid login details', async (done) => {
  testAdd(done, '2', 'Incorrect user name or password');

});

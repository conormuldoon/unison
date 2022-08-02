
import { enableFetchMocks } from 'jest-fetch-mock';
enableFetchMocks();
import fetchMock from 'jest-fetch-mock';


import { fireEvent, render, screen, act } from "@testing-library/react";
import LocationForm from './LocationForm';
import HttpStatus from 'http-status-codes';


it('renders without crashing', async () => {

  render(<LocationForm obtainData={() => { }} hideDisplay={() => { }} toggleDisplay={() => { }}
    display={true} selfRef="" containsRef="" />);

});


it('mathes snapshot', () => {


  const { container } = render(<LocationForm obtainData={() => { }} hideDisplay={() => { }}
    toggleDisplay={() => { }} display={true} selfRef="" containsRef="" />);

  expect(container).toMatchSnapshot();

});

async function changeValue(labelText: string, value: string) {

  const item = await screen.findByLabelText(labelText);
  await act(() => fireEvent.change(item, { target: { value: value } }));
}

it('sends the data in the form when submit is clicked', async () => {


  fetchMock.mockOnce(JSON.stringify({ value: false }));

  const hideDisplay = jest.fn();

  const location = 'UCD';

  const lon = '-6.223682';
  const lat = '53.308441';

  const obtainData = () => {


    expect(hideDisplay).toHaveBeenCalledTimes(1);

  };


  const component = <LocationForm display={true} containsRef={"http://localhost:8080/locationCollection/contains{?name}"} selfRef={"http://localhost:8080/locationCollection"} obtainData={obtainData} hideDisplay={hideDisplay}
    toggleDisplay={() => { }} />;
  render(component);

  await changeValue('Location name:', location);
  await changeValue('Longitude:', lon);
  await changeValue('Latitude:', lat);

  jest.spyOn(window, 'alert').mockImplementation(() => { });
  fetchMock.mockResponses(
    ['', { status: HttpStatus.ACCEPTED }],
    ['', { status: HttpStatus.OK }]);

  const button = await screen.findByText('Submit')
  fireEvent.click(button);
  await changeValue('Latitude:', '');


});

it('handles add location', async () => {
  const location = 'UCD';

  fetchMock.mockResponses(

    JSON.stringify({ value: false }),
    [
      JSON.stringify({ properties: { links: { harvest: '/harvest' } } }),
      { status: HttpStatus.OK }
    ],
    ['', { status: HttpStatus.ACCEPTED }],
    ['', { status: HttpStatus.OK }]);

  JSON.stringify({ properties: { links: { harvest: '/harvest' } } });
  jest.spyOn(window, 'alert').mockImplementation(() => { });
  const hideDisplay = jest.fn();


  const obtainData = () => {

    expect(alert).toBeCalledWith(location + ' was added.');

  };


  const component = <LocationForm display={true} containsRef={"http://localhost:8080/locationCollection/contains{?name}"} selfRef={"http://localhost:8080/locationCollection"}
    obtainData={obtainData} hideDisplay={hideDisplay}
    toggleDisplay={() => { }} />;
  render(component);

  await changeValue('Location name:', location);
  const button = await screen.findByText('Submit');
  fireEvent.click(button);
  await changeValue('Latitude:', '');
  expect(hideDisplay).toHaveBeenCalledTimes(1);

});


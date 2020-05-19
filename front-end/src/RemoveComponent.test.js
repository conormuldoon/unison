import "@testing-library/jest-dom/extend-expect";
import fetchMock from 'fetch-mock';
import React from 'react';
import ReactDOM from 'react-dom';
import { fireEvent, render } from "react-testing-library";
import RemoveComponent from './RemoveComponent';
import HttpStatus from 'http-status-codes';

const location = { name: 'UCD', links: { self: '/location/UCD' } };

it('renders without crashing', async () => {

  const div = document.createElement('div');

  ReactDOM.render(<RemoveComponent location={location}
    obtainData={() => { }} hideDisplay={() => { }} toggleDisplay={() => { }} display={true} />, div);
  ReactDOM.unmountComponentAtNode(div);
});

it('mathes snapshot', () => {


  const { container } = render(<RemoveComponent display={true} location={location} obtainData={() => { }} hideDisplay={() => { }} toggleDisplay={() => { }} />);

  expect(container).toMatchSnapshot();

});


it('handles remove location', async (done) => {

  fetchMock.delete('end:/' + location.name, HttpStatus.OK);


  const alertSpy = jest.spyOn(window, 'alert');
  alertSpy.mockImplementation(() => { });

  const confirmSpy = jest.spyOn(window, 'confirm');
  confirmSpy.mockImplementation(() => true);
  const hideDisplay = jest.fn();

  const obtainData = () => {
    expect(hideDisplay).toHaveBeenCalledTimes(1);
    expect(alert).toBeCalledWith(location.name + ' removed');
    done();
  };

  const component = <RemoveComponent location={location} display={true} obtainData={obtainData} hideDisplay={hideDisplay} toggleDisplay={() => { }} />;
  const { getByText } = render(component);

  fireEvent.click(getByText('Remove ' + location.name));

  fetchMock.restore();
  confirmSpy.mockClear();
  alertSpy.mockClear();

});



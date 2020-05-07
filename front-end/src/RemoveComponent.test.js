import "@testing-library/jest-dom/extend-expect";
import fetchMock from 'fetch-mock';
import React from 'react';
import ReactDOM from 'react-dom';
import { fireEvent, render } from "react-testing-library";
import RemoveComponent from './RemoveComponent';

import { SUCCESS, FAILURE } from './ResponseConstant';



it('renders without crashing', async () => {

  const div = document.createElement('div');

  ReactDOM.render(<RemoveComponent location='UCD'
    obtainData={() => { }} hideDisplay={() => { }} toggleDisplay={() => { }} display={true} />, div);
  ReactDOM.unmountComponentAtNode(div);
});

it('mathes snapshot', () => {


  const { container } = render(<RemoveComponent display={true} location='UCD' obtainData={() => { }} hideDisplay={() => { }} toggleDisplay={() => { }} />);

  expect(container).toMatchSnapshot();

});


it('handles remove location', async (done) => {
  const location = 'UCD';
  fetchMock.delete('end:/deleteLocation?location=' + location, String(SUCCESS));


  const alertSpy = jest.spyOn(window, 'alert');
  alertSpy.mockImplementation(() => { });

  const confirmSpy = jest.spyOn(window, 'confirm');
  confirmSpy.mockImplementation(() => true);
  const hideDisplay = jest.fn();

  const obtainData = () => {
    expect(hideDisplay).toHaveBeenCalledTimes(1);
    expect(alert).toBeCalledWith(location + ' removed');
    done();
  };

  const component = <RemoveComponent location={location} display={true} obtainData={obtainData} hideDisplay={hideDisplay} toggleDisplay={() => { }} />;
  const { getByText } = render(component);

  fireEvent.click(getByText('Remove ' + location));

  fetchMock.restore();
  confirmSpy.mockClear();
  alertSpy.mockClear();

});



it('displays that the location is not present', async (done) => {

  const location = 'UCD';
  fetchMock.delete('end:/deleteLocation?location=' + location, String(FAILURE));

  const alertSpy = jest.spyOn(window, 'alert');
  alertSpy.mockImplementation((messStr) => {
    expect(messStr).toEqual(location + ' is not being tracked by Unison.');
    done();
  });
  const confirmSpy = jest.spyOn(window, 'confirm');
  confirmSpy.mockImplementation(() => true);

  const component = <RemoveComponent location={location} display={true} obtainData={() => { }} toggleDisplay={() => { }} hideDisplay={() => { }} />;
  const { getByText } = render(component);



  fireEvent.click(getByText('Remove ' + location));

  fetchMock.restore();
  confirmSpy.mockClear();
  alertSpy.mockClear();
});
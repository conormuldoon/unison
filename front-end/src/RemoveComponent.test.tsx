import "@testing-library/jest-dom/extend-expect";
import fetchMock from 'fetch-mock';
import React from 'react';
import ReactDOM from 'react-dom';
import { fireEvent, render } from "@testing-library/react";
import RemoveComponent from './RemoveComponent';
import HttpStatus from 'http-status-codes';

const location = { name: 'UCD', links: { self: '/location/UCD' } };

const linksProperty = {
  "name": "UCD",
  "_links": {

    "self": {
      "href": "http://localhost:8080/locationCollection/UCD"
    }
  }

};

it('renders without crashing', async () => {

  const div = document.createElement('div');

  ReactDOM.render(<RemoveComponent href={linksProperty._links['self'].href} name={linksProperty.name}
    obtainData={() => { }} hideDisplay={() => { }}  />, div);
  ReactDOM.unmountComponentAtNode(div);
});

it('mathes snapshot', () => {


  const { container } = render(<RemoveComponent name={linksProperty.name} href={linksProperty._links['self'].href} obtainData={() => { }} hideDisplay={() => { }}  />);

  expect(container).toMatchSnapshot();

});


it('handles remove location', (done) => {

  fetchMock.delete('end:/' + location.name, HttpStatus.OK);


  const alertSpy = jest.spyOn(window, 'alert');
  alertSpy.mockImplementation(() => { });

  const confirmSpy = jest.spyOn(window, 'confirm');
  confirmSpy.mockImplementation(() => true);
  const hideDisplay = jest.fn();

  const obtainData = () => {
    expect(hideDisplay).toHaveBeenCalledTimes(1);
    expect(alert).toBeCalledWith(location.name + ' was removed.');
    done();
  };

  const component = <RemoveComponent name={linksProperty.name} href={linksProperty._links['self'].href} obtainData={obtainData} hideDisplay={hideDisplay}  />;
  const { getByText } = render(component);

  fireEvent.click(getByText('Remove ' + location.name));

  fetchMock.restore();
  confirmSpy.mockClear();
  alertSpy.mockClear();

});



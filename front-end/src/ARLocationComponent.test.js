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


it('toggles add location correctly', () => {
  const confirmSpy = jest.spyOn(window, 'confirm');
  confirmSpy.mockImplementation(() => true);

  const location = { name: 'UCD', links: { self: '/location/UCD' } };
  const { getByTestId, getByText } = render(<ARLocationComponent location={location} obtainData={() => { }} />);
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


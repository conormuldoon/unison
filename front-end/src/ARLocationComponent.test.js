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

  const { getByTestId, getByText } = render(<ARLocationComponent location={'UCD'} obtainData={() => { }} />);
  expect(getByTestId('lf-button')).toHaveTextContent('Add Location');
  expect(getByTestId('rf-button')).toHaveTextContent('Remove Location');
  fireEvent.click(getByText('Add Location'));
  expect(getByTestId('lf-button')).toHaveTextContent('Hide');
  expect(getByTestId('rf-button')).toHaveTextContent('Remove Location');
  fireEvent.click(getByText('Hide'));
  expect(getByTestId('lf-button')).toHaveTextContent('Add Location');
  expect(getByTestId('rf-button')).toHaveTextContent('Remove Location');
  fireEvent.click(getByText('Add Location'));
  fireEvent.click(getByText('Remove Location'));
  expect(getByTestId('lf-button')).toHaveTextContent('Add Location');
  expect(getByTestId('rf-button')).toHaveTextContent('Hide');
  fireEvent.click(getByText('Hide'));
  expect(getByTestId('lf-button')).toHaveTextContent('Add Location');
  expect(getByTestId('rf-button')).toHaveTextContent('Remove Location');

});

it('toggles remove location correctly', () => {

  const { getByTestId, getByText } = render(<ARLocationComponent location='UCD' obtainData={() => { }} />);
  expect(getByTestId('lf-button')).toHaveTextContent('Add Location');
  expect(getByTestId('rf-button')).toHaveTextContent('Remove Location');
  fireEvent.click(getByText('Remove Location'));
  expect(getByTestId('lf-button')).toHaveTextContent('Add Location');
  expect(getByTestId('rf-button')).toHaveTextContent('Hide');
  fireEvent.click(getByText('Hide'));
  expect(getByTestId('lf-button')).toHaveTextContent('Add Location');
  expect(getByTestId('rf-button')).toHaveTextContent('Remove Location');
  fireEvent.click(getByText('Remove Location'));
  fireEvent.click(getByText('Add Location'));
  expect(getByTestId('lf-button')).toHaveTextContent('Hide');
  expect(getByTestId('rf-button')).toHaveTextContent('Remove Location');
  fireEvent.click(getByText('Hide'));
  expect(getByTestId('lf-button')).toHaveTextContent('Add Location');
  expect(getByTestId('rf-button')).toHaveTextContent('Remove Location');

});

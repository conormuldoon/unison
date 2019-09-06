import React from 'react';
import ReactDOM from 'react-dom';
import ARLocationComponent from './ARLocationComponent';
import { act } from 'react-dom/test-utils';
import {fireEvent,render,waitForElement} from "react-testing-library";
import "@testing-library/jest-dom/extend-expect";
import fetchMock from 'fetch-mock';

it('renders without crashing', async () => {

  const div = document.createElement('div');

  ReactDOM.render(<ARLocationComponent />,div);
  ReactDOM.unmountComponentAtNode(div);
});




it('mathes snapshot', () => {


  const {container} = render(<ARLocationComponent />);

  expect(container).toMatchSnapshot();

});



it('toggles add location correctly', () => {

  const {getByTestId,getByText} = render(<ARLocationComponent location={'UCD'} />);
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

  const {getByTestId,getByText} = render(<ARLocationComponent location='UCD'/>);
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

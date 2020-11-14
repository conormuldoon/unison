import ReactDOM from 'react-dom';
import { render } from "@testing-library/react";
import DateSelector from './DateSelector';
import React from 'react';


const dateSelector = <DateSelector label={'Test Label'} dateValue={'11-11-2020'} handleDayChange={() => { }} />;

it('renders without crashing', () => {
  const div = document.createElement('div');


  ReactDOM.render(dateSelector, div);
  ReactDOM.unmountComponentAtNode(div);
});



it('mathes snapshot', () => {


  const { container } = render(dateSelector);

  expect(container).toMatchSnapshot();

});
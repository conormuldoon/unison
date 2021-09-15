import ReactDOM from 'react-dom';
import { render } from "@testing-library/react";
import DateSelector from './DateSelector';
import React from 'react';

function handle(d: Date) {

}
const dateSelector = <DateSelector label={'Test Label'} dateValue={'14/9/2021'} handleDayChange={handle} />;

it('renders without crashing', () => {
  const div = document.createElement('div');


  ReactDOM.render(dateSelector, div);
  ReactDOM.unmountComponentAtNode(div);
});



it('mathes snapshot', () => {


  const { container } = render(dateSelector);

  expect(container).toMatchSnapshot();

});

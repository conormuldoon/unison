import React from 'react';
import ReactDOM from 'react-dom';
import DateSelector from './DateSelector';
import {FORMAT} from './Constant';

import { render} from "react-testing-library";

import  {
  formatDate
} from 'react-day-picker/moment';

const date=formatDate(new Date(), FORMAT);

const dateSelector=<DateSelector label={'Test Label'} dateValue={date} handleDayChange={()=>{}}/>;

it('renders without crashing', () => {
  const div = document.createElement('div');


  ReactDOM.render(dateSelector, div);
  ReactDOM.unmountComponentAtNode(div);
});



  it('mathes snapshot', () => {


      const {container} = render(dateSelector);

      expect(container).toMatchSnapshot();

  });

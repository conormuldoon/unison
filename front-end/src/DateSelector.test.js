import React from 'react';
import ReactDOM from 'react-dom';
import DateSelector from './DateSelector';
import {FORMAT} from './Constant';

import { render} from "react-testing-library";

import  {
  formatDate
} from 'react-day-picker/moment';



const dateSelector=<DateSelector label={'Test Label'} dateValue={'23/10/2019'} handleDayChange={()=>{}}/>;

it('renders without crashing', () => {
  const div = document.createElement('div');


  ReactDOM.render(dateSelector, div);
  ReactDOM.unmountComponentAtNode(div);
});



  it('mathes snapshot', () => {


      const {container} = render(dateSelector);

      expect(container).toMatchSnapshot();

  });

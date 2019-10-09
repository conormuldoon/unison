import React from 'react';

import DayPickerInput from 'react-day-picker/DayPickerInput';
import 'react-day-picker/lib/style.css';

import  {
  formatDate,
  parseDate,
} from 'react-day-picker/moment';

import {FORMAT} from './Constant';

/**
 * A component for selecting a date.
 * 
 */
export default function DateSelector(props){


    return (
      <div>
        <p>{props.label}</p>
        <DayPickerInput

          format={FORMAT}
          formatDate={formatDate}
          parseDate={parseDate}
          value={props.dateValue}
          onDayChange={props.handleDayChange}


        />
      </div>
    );

}

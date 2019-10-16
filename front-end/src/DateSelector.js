import React from 'react';

import DayPickerInput from 'react-day-picker/DayPickerInput';
import 'react-day-picker/lib/style.css';

import  {
  formatDate,
  parseDate,
} from 'react-day-picker/moment';

import {FORMAT} from './Constant';

import PropTypes from 'prop-types';

/**
 * A component for selecting a date.
 * 
 * @component
 * 
 */
function DateSelector(props){


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

DateSelector.propTypes ={
  
  /** The label to display over the selector. */
  label: PropTypes.string,

  /** The initial date value to display. */
  dateValue: PropTypes.string,

  /** A callback function called when the date is changed. */
  handleDayChange: PropTypes.func,

}

export default DateSelector;
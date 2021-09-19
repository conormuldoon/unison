import React from 'react';
import DayPickerInput from 'react-day-picker/DayPickerInput';
import PropTypes from 'prop-types';
import 'react-day-picker/lib/style.css';
import { formatDate, parseDate } from 'react-day-picker/moment';

import { FORMAT } from './Constant';

interface SelectorProps {
  label: string;
  dateValue: Date;
  handleDayChange: (day: Date) => void;
}
/**
 * Creates a component for selecting a date.
 * 
 */
function DateSelector({ label, dateValue, handleDayChange }: SelectorProps): JSX.Element {


  return (
    <div>
      <p>{label}</p>
      <DayPickerInput

        format={FORMAT}
        formatDate={formatDate}
        parseDate={parseDate}
        value={dateValue}
        onDayChange={handleDayChange}


      />
    </div>
  );

}

DateSelector.propTypes = {

  /** The label to display over the selector. */
  label: PropTypes.string.isRequired,

  /** The initial date value to display. */
  dateValue: PropTypes.string.isRequired,

  /** A callback function called when the date is changed. */
  handleDayChange: PropTypes.func.isRequired,

}



export default DateSelector;
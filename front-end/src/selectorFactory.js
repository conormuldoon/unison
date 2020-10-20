import React from 'react';
import DayPickerInput from 'react-day-picker/DayPickerInput';
import 'react-day-picker/lib/style.css';
import { formatDate, parseDate } from 'react-day-picker/moment';
import { FORMAT } from './Constant';


/**
 * Creates a component for selecting a date.
 * 
 */
function createSelector(label, dateValue, handleDayChange) {


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



export default createSelector;
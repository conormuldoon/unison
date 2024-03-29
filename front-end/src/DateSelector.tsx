
import DayPickerInput from 'react-day-picker/DayPickerInput';
import PropTypes from 'prop-types';
import 'react-day-picker/lib/style.css';

import { FORMAT } from './Constant';

export interface SelectorProps {

  /**
   * Tha label dispalyed above the date selector.
   */
  label: string;

  /**
   * The initial value of the date selector.
   */
  dateValue: string;

  /**
   * A callback invoked when the selected date is changed.
   */
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
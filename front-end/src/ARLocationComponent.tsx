
import React, { useState } from 'react';
import './App.css';

import PropTypes from 'prop-types';

export interface ARLocationProps {

  /** A factory that creates a add location form. */
  createLocation: (toggleDisplayAdd: () => void, displayAdd: boolean, hideAdd: () => void) => React.ReactNode;

  /** A factory that creates a component from removing locations. */
  createRemove: ((hideAdd: () => void) => React.ReactNode) | null;
}

/**
 * A component that enables locations for tracking to be either added or removed. 
 * The user name and password is posted to the server and the obtain data callback is executed if successful.
 * 
 * @component
 * 
 */
function ARLocationComponent({ createLocation, createRemove }: ARLocationProps): JSX.Element {

  const [displayAdd, setDisplayAdd] = useState(false);

  function toggleDisplayAdd() {

    setDisplayAdd(!displayAdd);
  }

  function hideAdd() {
    if (displayAdd)
      setDisplayAdd(false);
  }

  return (
    <div>
      <div className='pLeft'>
        {createLocation(toggleDisplayAdd, displayAdd, hideAdd)}
      </div>
      {createRemove && <div className='pLeft'>
        {createRemove(hideAdd)}
      </div>}
    </div>
  );
}

ARLocationComponent.propTypes = {


  createLocation: PropTypes.func.isRequired,
  createRemove: PropTypes.func,
}


export default ARLocationComponent;
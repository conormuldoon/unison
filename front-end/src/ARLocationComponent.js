import PropTypes from 'prop-types';
import React, { useState } from 'react';
import './App.css';
import LocationForm from './LocationForm';
import RemoveComponent from './RemoveComponent';




/**
 * A component that enables locations for tracking to be either added or removed. 
 * The user name and password is posted to the server and the obtain data callback is executed if successful.
 * 
 * @component
 * 
 */
function ARLocationComponent(props) {

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
        <LocationForm obtainData={props.obtainData} toggleDisplay={toggleDisplayAdd}
          display={displayAdd} hideDisplay={hideAdd} />

      </div>
      {props.linksProperty && <div className='pLeft'>
        <RemoveComponent obtainData={props.obtainData} hideDisplay={hideAdd} linksProperty={props.linksProperty} />
      </div>}
    </div>
  );
}


ARLocationComponent.propTypes = {


  /** Called when a location has been successfully added/removed to obtain the updated location list from the server and update the locations displayed on the map. */
  obtainData: PropTypes.func.isRequired,
  linksPropert: PropTypes.object,
}

export default ARLocationComponent;
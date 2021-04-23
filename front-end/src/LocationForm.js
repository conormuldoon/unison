
import PropTypes from 'prop-types';
import React, { useState } from 'react';
import './App.css';

import { problemConnecting } from './Util';
import HttpStatus from 'http-status-codes';
import { locationPutObject } from './Util';

import parser from 'uri-template';

export function createLocationFactory(obtainData, selfRef, containsRef) {

  return function locationFactory(toggleDisplayAdd, displayAdd, hideAdd) {
      return <LocationForm obtainData={obtainData} toggleDisplay={toggleDisplayAdd}
          display={displayAdd} hideDisplay={hideAdd} selfRef={selfRef} containsRef={containsRef} />;
  }
}

/**
 * A component for displaying a form to add new locations to be tracked.
 * 
 * @component 
 */
function LocationForm({ display, hideDisplay, toggleDisplay, obtainData, selfRef, containsRef }) {



  const [location, setLocation] = useState('');
  const [lon, setLon] = useState('');
  const [lat, setLat] = useState('');

  function handleLoc(event) {
    setLocation(event.target.value);

  }

  function handleLon(event) {
    setLon(event.target.value);
  }

  function handleLat(event) {
    setLat(event.target.value);

  }

  function updateDisplay() {
    setLocation('');
    setLon('');
    setLat('');
    hideDisplay();
    obtainData();
  }

  async function putData(selfRef) {

    const response = await fetch(selfRef,
      locationPutObject(location, lon, lat));



    if (response.ok) {

      const harvestResponse = await fetch(selfRef + '/' + location, {
        method: 'POST'
      });

      const message = (response.status === HttpStatus.OK) ? 'updated' : 'added';

      if (harvestResponse.ok) {
        alert(location + ' was ' + message + '.');
        updateDisplay();
      } else if (harvestResponse.status === HttpStatus.BAD_GATEWAY) {
        alert(location + ' was ' + message + ', but did did not recieve the weather data. The ' +
          lon + " and " + lat + " longitude and latitude coordinates may not be covered by the model.");
        updateDisplay();
      }
      else {
        alert(location + ' was ' + message + ', but Unison did not obtain the weather data.')
        updateDisplay();
      }
    } else if (response.status === HttpStatus.UNAUTHORIZED) {
      alert('Incorrect user name or password');
    } else if (response.status === HttpStatus.FORBIDDEN) {
      alert('You do not have permission to update ' + location +
        '. A location of the same name may have been added by another user.');
    } else {
      problemConnecting();
    }

  }

  async function handleSubmit(event) {
    event.preventDefault();

    const template = parser.parse(containsRef);
    const uri = template.expand({ name: location });

    const response = await fetch(uri);

    if (response.ok) {

      const contains = await response.json();
      if (contains.value) {

        if (window.confirm('A location named ' + location + " was previously added. Would you like to update it?")) {
          putData(selfRef);
        }

      } else {
        putData(selfRef);
      }

    } else {
      problemConnecting();
    }


  }


  let text = 'Add Location';
  if (display) {
    text = 'Hide';
  }


  return (<div id='pLeft'>

    <button data-testid="lf-button" onClick={toggleDisplay}>{text}</button>

    {display && <form id='locForm' onSubmit={handleSubmit}>
      <label>
        Location name: <input type="text" name="name" value={location} onChange={handleLoc} required />
      </label>
      <br />
      <br />
      <label>
        Longitude: <input type="number" step="any" name="lon" value={lon} onChange={handleLon} required />
      </label>
      <br />
      <br />
      <label>
        Latitude: <input type="number" step="any" name="lat" value={lat} onChange={handleLat} required />
      </label>
      <br />
      <br />
      <div>
        <input type="submit" value="Submit" />
      </div>
    </form>}
  </div>


  );

}

LocationForm.propTypes = {
  /** Determines whether the form for entering location name, longitude, and latitude values is displayed. */
  display: PropTypes.bool.isRequired,

  /** Toggles whether the form is displayed. If the remove form is displayed, it will be hidden. */
  toggleDisplay: PropTypes.func.isRequired,

  /** Called when a location has been successfully added to hide the form. */
  hideDisplay: PropTypes.func.isRequired,

  /** Called when a location has been successfully added to obtain the updated location list from the server and add the location to the map. */
  obtainData: PropTypes.func.isRequired,

  featureProperties: PropTypes.object,

  selfRef: PropTypes.string,

  containsRef: PropTypes.string,

}

export default LocationForm;

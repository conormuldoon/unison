
import PropTypes from 'prop-types';
import React, { useState } from 'react';
import './App.css';
import { API } from './Constant';
import { problemConnecting } from './Util';
import HttpStatus from 'http-status-codes';
import { locationPutObject } from './Util';



/**
 * A component for displaying a form to add new locations to be tracked.
 * 
 * @component 
 */
function LocationForm(props) {



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
    props.hideDisplay();
    props.obtainData();
  }

  async function putData(locationExists) {

    const response = await fetch(API + '/location',
      locationPutObject(location, lon, lat));

    let message;
    if (locationExists) {
      message = 'updated';
    } else {
      message = 'added';
    }

    if (response.ok) {

      const harvestPath = await response.json();


      const harvestResponse = await fetch(API + harvestPath, {
        method: 'POST',
        body: location
      });

      if (harvestResponse.ok) {
        alert(location + ' was ' + message + '.');
        updateDisplay();
      } else if (harvestResponse.status === HttpStatus.BAD_GATEWAY) {
        alert(location + ' was ' + message + ', but did did not recieve the weather data. The ' +
          lon + " and " + lat + " longitude and latitude coordinates may not be covered by the model.");
      }
      else {
        alert(location + ' was ' + message + ', but Unison did not obtain the weather data.')
        updateDisplay();
      }
    } else if (response.status === HttpStatus.UNAUTHORIZED) {
      alert('Incorrect user name or password');
    } else if (response.status === HttpStatus.FORBIDDEN) {
      alert('A location named ' + location + ' is being tracked by another user and was not ' + message + '.')
    }
    else {
      problemConnecting();
    }
  }

  async function handleSubmit(event) {
    event.preventDefault();
    if (props.featureProperties.has(location)) {

      if (window.confirm(location + ' already exists. Would you like to replace it?')) {
        putData(true);
      }

    } else {
      putData(false);
    }

  }


  let text = 'Add Location';
  if (props.display) {
    text = 'Hide';
  }


  return (<div id='pLeft'>

    <button data-testid="lf-button" onClick={props.toggleDisplay}>{text}</button>

    {props.display && <form id='locForm' onSubmit={handleSubmit}>
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
}

export default LocationForm;


import PropTypes from 'prop-types';
import React, { useState } from 'react';
import './App.css';

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

  async function putData() {

    const response = await fetch('/', {
      method: 'GET',
      headers: new Headers({
        'Accept': 'application/hal+json'
      })
    });

    if (response.ok) {

      const model = await response.json();

      const putResponse = await fetch(model._links.locationCollection.href,
        locationPutObject(location, lon, lat));

      if (putResponse.ok) {

        const harvestResponse = await fetch(model._links.locationCollection.href + '/' + location, {
          method: 'POST'
        });


        if (harvestResponse.ok) {
          alert(location + ' was added.');
          updateDisplay();
        } else if (harvestResponse.status === HttpStatus.BAD_GATEWAY) {
          alert(location + ' was added, but did did not recieve the weather data. The ' +
            lon + " and " + lat + " longitude and latitude coordinates may not be covered by the model.");
        }
        else {
          alert(location + ' was added, but Unison did not obtain the weather data.')
          updateDisplay();
        }
      } else if (response.status === HttpStatus.UNAUTHORIZED) {
        alert('Incorrect user name or password');
      } else if (response.status === HttpStatus.FORBIDDEN) {
        alert('You do not have permission to add ' + location + '. A location of the same name may have been added by another user.');
      }
      else {
        problemConnecting();
      }
    } else {
      problemConnecting();
    }
  }

  async function handleSubmit(event) {
    event.preventDefault();

    putData();

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

  linksProperty: PropTypes.object,
}

export default LocationForm;

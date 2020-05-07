
import PropTypes from 'prop-types';
import React from 'react';
import { API } from './Constant';
import { problemConnecting } from './Util';
import HttpStatus from 'http-status-codes';
import { SUCCESS } from './ResponseConstant';

/**
 * A component to enable the user to remove a location from being tracked.
 * 
 * @component
 */
function RemoveComponent(props) {

  async function removeRequest() {

    const response = await fetch(API + '/deleteLocation?location=' + props.location,
      {
        method: 'DELETE'
      });

    if (response.ok) {
      const resVal = await response.json();

      if (resVal === SUCCESS) {

        alert(props.location + ' removed');
        props.obtainData();

      } else {
        alert(props.location + ' is not being tracked by Unison.');

      }

    } else if (response.status === HttpStatus.UNAUTHORIZED) {
      alert('Incorrect user name or password');
    } else if (response.status === HttpStatus.FORBIDDEN) {
      alert('You do not have permission to delete ' + props.location + '.');
    } else {
      problemConnecting();
    }

  }

  function handleClick() {
    props.hideDisplay();
    if (window.confirm("Are you sure you would like to remove " + props.location + "?")) {
      removeRequest();
    }
  }


  return (

    <button data-testid='rm-button' onClick={handleClick}>{'Remove ' + props.location}</button>

  );

}


RemoveComponent.propTypes = {

  /** The name of the location to be removed. */
  location: PropTypes.string.isRequired,

  /** Called when the remove button is clicked to hide the add location form if it is displayed. */
  hideDisplay: PropTypes.func.isRequired,

  /** Called when a location has been successfully removed to obtain the updated location list from the server. */
  obtainData: PropTypes.func.isRequired,
}

export default RemoveComponent;

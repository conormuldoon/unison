
import PropTypes from 'prop-types';
import React from 'react';
import { API } from './Constant';
import { problemConnecting } from './Util';
import HttpStatus from 'http-status-codes';

/**
 * A component to enable the user to remove a location from being tracked.
 * 
 * @component
 */
function RemoveComponent(props) {

  async function removeRequest() {

    const response = await fetch(API + '/location/' + props.location.name,
      {
        method: 'DELETE'
      });

    if (response.ok) {

      alert(props.location.name + ' removed');
      props.obtainData();

    } else if (response.status === HttpStatus.UNAUTHORIZED) {
      alert('Incorrect user name or password');
    } else if (response.status === HttpStatus.FORBIDDEN) {
      alert('You do not have permission to delete ' + props.location + '.');
    } else {
      console.log(response.status)
      problemConnecting();
    }

  }

  function handleClick() {
    props.hideDisplay();
    if (window.confirm("Are you sure you would like to remove " + props.location.name +
      "? This will also delete all of the harvested data assoicated with " + props.location.name + ".")) {
      removeRequest();
    }
  }


  return (

    <button data-testid='rm-button' onClick={handleClick}>{'Remove ' + props.location.name}</button>

  );

}


RemoveComponent.propTypes = {

  /** The name of the location to be removed. */
  location: PropTypes.object.isRequired,

  /** Called when the remove button is clicked to hide the add location form if it is displayed. */
  hideDisplay: PropTypes.func.isRequired,

  /** Called when a location has been successfully removed to obtain the updated location list from the server. */
  obtainData: PropTypes.func.isRequired,
}

export default RemoveComponent;

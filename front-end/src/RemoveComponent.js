
import PropTypes from 'prop-types';
import React from 'react';
import { problemConnecting } from './Util';
import HttpStatus from 'http-status-codes';


export function createRemoveFactory(obtainData, href, name) {

  return function removeFactory(hideDisplay) {
      return <RemoveComponent obtainData={obtainData}
          hideDisplay={hideDisplay}
          href={href}
          name={name}
      />;
  }
}

/**
 * A component to enable the user to remove a location from being tracked.
 * 
 * @component
 */
function RemoveComponent({obtainData, hideDisplay, href, name }) {

  async function removeRequest() {

    const response = await fetch(href, {
      method: 'DELETE'
    });

    if (response.ok) {

      alert(name + ' was removed.');
      obtainData();

    } else if (response.status === HttpStatus.UNAUTHORIZED) {
      alert('Incorrect user name or password');
    } else if (response.status === HttpStatus.FORBIDDEN) {
      alert('You do not have permission to delete ' + name + '.');
    } else {
      console.log(response.status)
      problemConnecting();
    }

  }

  function handleClick() {
    hideDisplay();
    if (window.confirm("Are you sure you would like to remove " + name +
      "? This will also delete all of the harvested data assoicated with " + name + ".")) {
      removeRequest();
    }
  }


  return (

    <button data-testid='rm-button' onClick={handleClick}>{'Remove ' + name}</button>

  );

}


RemoveComponent.propTypes = {

  /** Called when the remove button is clicked to hide the add location form if it is displayed. */
  hideDisplay: PropTypes.func.isRequired,

  /** Called when a location has been successfully removed to obtain the updated location list from the server. */
  obtainData: PropTypes.func.isRequired,

  name: PropTypes.string.isRequired,

  href: PropTypes.string.isRequired,
}

export default RemoveComponent;


import PropTypes from 'prop-types';
import React from 'react';
import { problemConnecting } from './Util';
import HttpStatus from 'http-status-codes';
import parser from 'uri-template';
import { LOCATION } from './Constant';

/**
 * A component to enable the user to remove a location from being tracked.
 * 
 * @component
 */
function RemoveComponent(props) {

  async function removeRequest() {

    const href = props.linksProperty[LOCATION].href;
    const template = parser.parse(href);
    const uri = template.expand({ name: props.location.name });

    const response = await fetch(uri,
      {
        method: 'DELETE'
      });
      
    if (response.ok) {

      alert(props.location.name + ' was removed.');
      props.obtainData();

    } else if (response.status === HttpStatus.UNAUTHORIZED) {
      alert('Incorrect user name or password');
    } else if (response.status === HttpStatus.FORBIDDEN) {
      alert('You do not have permission to delete ' + props.location.name + '.');
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

  linksProperty: PropTypes.object
}

export default RemoveComponent;



import { problemConnecting, csrfToken } from './Util';
import HttpStatus from 'http-status-codes';
import PropTypes from 'prop-types';


export function createRemoveFactory(obtainData: () => void, name: string, href: string) {

  return function removeFactory(hideAdd: () => void): JSX.Element {

    return <RemoveComponent obtainData={obtainData}
      hideAdd={hideAdd}
      href={href}
      name={name}
    />;

  }
}

export interface RemoveProps {

  /** Called when a location has been successfully removed to obtain the updated location list from the server. */
  obtainData: () => void;

  /** Called when the remove button is clicked to hide the add location form if it is displayed. */
  hideAdd: () => void;


  /** The URI used to delete the lcoation. */
  href: string;

  /** The name of the currently selected location. */
  name: string;

}

const DELETE = 'DELETE';


/**
 * A component to enable the user to remove a location from being tracked.
 * 
 * @component
 */
function RemoveComponent({ obtainData, hideAdd, href, name }:
  RemoveProps): JSX.Element {

  function handleResponse(ok: boolean, status: number) {
    if (ok) {
      alert(name + ' was removed.');
      obtainData();
    } else if (status === HttpStatus.UNAUTHORIZED) {
      alert('Incorrect user name or password');
    }
    else {
      console.log(status)
      problemConnecting();
    }
  }


  async function removeRequest() {

    let csrfT = csrfToken();

    if (csrfT === '') {
      const response = await fetch(href, { method: DELETE });

      if (response.status === HttpStatus.FORBIDDEN) {
        csrfT = csrfToken();
      } else {
        // CSRF is disabled.
        handleResponse(response.ok, response.status);
        return;
      }
    }

    const response = await fetch(href, {
      method: DELETE,
      headers: new Headers({
        'X-XSRF-TOKEN': csrfT
      })
    });

    if (response.status === HttpStatus.FORBIDDEN) {

      alert('You do not have permission to delete ' + name + '. You may need to enter your login details.');

    } else {
      handleResponse(response.ok, response.status);
    }

  }

  function handleClick() {
    hideAdd();
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

  hideAdd: PropTypes.func.isRequired,

  obtainData: PropTypes.func.isRequired,

  name: PropTypes.string.isRequired,

  href: PropTypes.string.isRequired,
}


export default RemoveComponent;

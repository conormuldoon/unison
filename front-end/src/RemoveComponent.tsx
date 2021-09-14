

import React from 'react';
import { problemConnecting } from './Util';
import HttpStatus from 'http-status-codes';


export function createRemoveFactory(obtainData: () => void, name: string, href?: string) {

  return function removeFactory(hideAdd: () => void): JSX.Element | undefined {
    if (href) {
      return <RemoveComponent obtainData={obtainData}
        hideAdd={hideAdd}
        href={href}
        name={name}
      />;
    }
  }
}

interface RemoveProps {
  obtainData: () => void;
  hideAdd: () => void;
  href: string;
  name: string;

}
/**
 * A component to enable the user to remove a location from being tracked.
 * 
 * @component
 */
function RemoveComponent({ obtainData, hideAdd, href, name }:
  RemoveProps): JSX.Element {

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



export default RemoveComponent;

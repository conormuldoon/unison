import React, { useState,} from 'react';
import LocationForm from './LocationForm';
import RemoveForm from './RemoveForm';
import './App.css';


import PropTypes from 'prop-types';


/**
 * A component that enables locations for tracking to be either added or removed. 
 * The user name and password is posted to the server and the obtain data callback is executed if successful.
 * 
 */
export default function ARLocationComponent(props) {

    const [displayAdd,setDisplayAdd]=useState(false);
    const [displayRemove,setDisplayRemove]=useState(false);

    function toggleDisplayAdd(){
      if(displayRemove&&!displayAdd){
        setDisplayAdd(true);
        setDisplayRemove(false);
      }else{
        setDisplayAdd(!displayAdd);
      }
    }

    function toggleDisplayRemove(){
      if(displayAdd&&!displayRemove){
        setDisplayAdd(false);
        setDisplayRemove(true);
      }else{
        setDisplayRemove(!displayRemove);
      }
    }

    function hideAdd(){
      setDisplayAdd(false);
    }

    function hideRemove(){
      setDisplayRemove(false);
    }

    return (
      <div>
        <div className='pLeft'>
          <LocationForm obtainData={props.obtainData} toggleDisplay={toggleDisplayAdd} display={displayAdd} hideDisplay={hideAdd} />

        </div>
        {props.location&&<div className='pLeft'>
          <RemoveForm obtainData={props.obtainData} toggleDisplay={toggleDisplayRemove} display={displayRemove} hideDisplay={hideRemove} location={props.location} />
        </div>}
      </div>
    );
}


ARLocationComponent.propTypes ={
  /** The currently selected location. */
  location: PropTypes.string,

  /** Called when a location has been successfully added/removed to obtain the updated location list from the server and update the locations displayed on the map. */
  obtainData: PropTypes.func,
}

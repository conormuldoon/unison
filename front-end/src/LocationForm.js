
import React, { useState } from 'react';
import {API} from './Constant';
import './App.css';
import {locationPostObject} from './Util';

import PropTypes from 'prop-types';


/**
 * A component for displaying a form to add new locations to be tracked.
 *  
 */
export default function LocationForm(props) {

  

  const [location,setLocation]=useState('');
  const [lon,setLon]=useState('');
  const [lat,setLat]=useState('');
  const [uname,setUName]=useState('');
  const [pword,setPWord]=useState('');


  function handleLoc(event){
    setLocation(event.target.value);

  }

  function handleLon(event){
    setLon(event.target.value);
  }

  function handleLat(event){
    setLat(event.target.value);

  }

  function handleUN(event){
    setUName(event.target.value);
  }

  function handlePassw(event){
    setPWord(event.target.value);
  }

  function handleSubmit(event){

    postData();

    event.preventDefault();

   }

   async function postData(){

    const response=await fetch(API+'/addLocation',
      locationPostObject(location,uname,pword,lon,lat));



    if(response.ok){
      const resVal=await response.json();

      if(resVal===0){
        alert('Location already exists');
      }else if(resVal===1){
        alert(location+' added');
        updateDisplay();

      }else if(resVal===2){
        alert('Incorrect user name or password');
      }else{
        alert(location + ' added, but did not obtain weather data.')
        updateDisplay();
      }
    }else{
      alert('Problem connecting');
    }

  }



  function updateDisplay(){
    setLocation('');
    setLon('');
    setLat('');
    setUName('');
    setPWord('');
    props.hideDisplay();
    props.obtainData();
  }


    let text='Add Location';
    if(props.display){
      text='Hide';
    }



    return ( <div id='pLeft'>

          <button data-testid="lf-button" onClick={props.toggleDisplay}>{text}</button>

          {props.display&&<form  id='locForm' onSubmit={handleSubmit}>
              <label>
                Location name: <input type="text" name="name" value={location} onChange={handleLoc} required  />
              </label>
              <br/>
              <br/>
              <label>
                Longitude: <input type="number" step="any" name="lon" value={lon} onChange={handleLon} required  />
              </label>
              <br/>
              <br/>
              <label>
                Latitude: <input type="number" step="any" name="lat"  value={lat} onChange={handleLat} required  />
              </label>
              <br/>
              <br/>
              <label>
                Username: <input type="text" name="un" onChange={handleUN}  value={uname}  required />
              </label>
              <br/>
              <br/>
              <label>
                Password: <input type="password" name="passwd" onChange={handlePassw}  value={pword} required />
              </label>
              <br/>
              <br/>
              <div>
                  <input type="submit" value="Submit" />
              </div>
            </form>}
        </div>


    );

}

LocationForm.propTypes ={
  /** Determines whether the form for entering location name, longitude, and latitude values is displayed. */
  display: PropTypes.bool,

  /** Toggles whether the form is displayed. If the remove form is displayed, it will be hidden. */
  toggleDisplay: PropTypes.func,

  /** Called when a location has been successfully added to hide the form. */
  hideDisplay: PropTypes.func,

  /** Called when a location has been successfully added to obtain the updated location list from the server and add the location to the map. */
  obtainData: PropTypes.func,
}

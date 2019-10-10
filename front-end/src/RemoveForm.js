
import React, { useState } from 'react';
import {API} from './Constant';
import {removePostObject} from './Util';

import PropTypes from 'prop-types';

/**
 * A component to display a form to enable the user to enter their credentials to remove a location from being tracked.
 * 
 */
export default function RemoveForm(props) {


  const [uname,setUName]=useState('');
  const [pword,setPWord]=useState('');


  function handleUN(event){
    setUName(event.target.value);
  }

  function handlePassw(event){
    setPWord(event.target.value);
  }

  function handleSubmit(event){

    removeRequest();

    event.preventDefault();

   }

   async function removeRequest(){

    const response=await fetch(API+'/deleteLocation',
      removePostObject(props.location,uname,pword));

    if(response.ok){
      const resVal=await response.json();

      if(resVal===0){
        alert('Location does not exist');

      }else if(resVal===1){

        alert(props.location+' removed');
        clear();
        props.obtainData();

      }else if (resVal===3){
        alert('You do not have permission to delete this location');
      }else{
        alert('Incorrect user name or password');
      }
    }else{
      alert('Problem connecting');
    }

  }

  function clear(){
    setUName('');
    setPWord('');
    props.hideDisplay();
  }



    let text='Remove Location';
    if(props.display){
      text='Hide';
    }

    return (

      <div>

          <button data-testid='rf-button' onClick={props.toggleDisplay}>{text}</button>

          {props.display&&<form  id='locForm' onSubmit={handleSubmit}>
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
                  <input  type="submit" value={"Remove "+props.location} />
              </div>
            </form>}
        </div>


    );

}


RemoveForm.propTypes ={
 
  /** The name of the location to be removed. */
  location: PropTypes.string,

  /** Determines whether the form is displayed. */
  display: PropTypes.bool,

  /** Toggles whether the form is displayed. If the location form is displayed, it will be hidden. */
  toggleDisplay: PropTypes.func,

  /** Called when a location has been successfully removed to hide the form. */
  hideDisplay: PropTypes.func,

  /** Called when a location has been successfully removed to obtain the updated location list from the server. */
  obtainData: PropTypes.func,
}

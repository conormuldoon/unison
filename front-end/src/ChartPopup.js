import React from 'react';

import {IoMdClose} from "react-icons/io";

import './App.css';
import TabsComponent from './TabsComponent';

export default function ChartPopup(props){


  function handleClick(e){
    e.stopPropagation();
  }

    // Changing second word in current variable to lower case if present
    let vca=props.varCur.split(' ');
    let vc=vca[0];
    if(vca.length>1){
      vca[1]=vca[1].toLowerCase();
      vc+=' '+vca[1];
    }

    return (
      <div id="popupdiv" data-testid='chart-div' onClick={handleClick}>
        <div id="iicon">
          <IoMdClose onClick={props.closePopup} size={20}  color="rgb(192, 57, 43)" />

        </div>
        <center>
          {vc} data From {props.location.trim()}

          <TabsComponent varCur={props.varCur} location={props.location} fromDate={props.fromDate} toDate={props.toDate}/>

        </center>
      </div>
    );

}

import React, { useState, useEffect } from 'react';

import {IoMdClose} from "react-icons/io";

import './App.css';
import TabsComponent from './TabsComponent';
import {varMapping} from './Util';

import {PRECIP,API} from './Constant';

import PropTypes from 'prop-types';

const DLEN=16;

/**
 * A popup that displays a TabsComponent. Once mounted, it connects to the back-end to obtain data
 * for the from date, to date, location, and weather variable selected.
 * 
 * @component
 */
function ChartPopup(props){

  const [data,setData]=useState(undefined);
  const [zoomDomain,setZoomDomain]=useState(undefined);
  const [minMax,setMinMax]=useState(false);

  useEffect(()=>{
    
    async function obtainData(){
      const variRequest=varMapping(props.varCur);

      
      const loc=props.location.trim();
      const request=API+'/'+variRequest+'?location='+loc+"&fromDate="+props.fromDate+"&toDate="+props.toDate;

      let response = await fetch(request);
      let dataArray = await response.json();
    
      const n=dataArray.length;

      for(let i=0;i<n;i++){
        const dateS=dataArray[i].date;
        dataArray[i].date=new Date(dateS.substring(0,DLEN));

      }

      if(n>0){

        const fd=dataArray[0].date;
        const td=dataArray[dataArray.length-1].date;

        if(props.varCur === PRECIP&&dataArray[0].precipitation.minvalue!==undefined){

          setMinMax(true);
        }else{
          setMinMax(false);
        }

        setData(dataArray);
        setZoomDomain({ x: [fd, td] });
      }else{

        setData(undefined);
        setZoomDomain(undefined);
        setMinMax(false);
      }
      
    }
    obtainData();
    

  },[props.varCur,props.location,props.fromDate,props.toDate]);

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
          {vc} data from {props.location.trim()}

          <TabsComponent varCur={props.varCur} data={data} zoomDomain={zoomDomain} minMax={minMax} 
            setZoomDomain={setZoomDomain}/>

        </center>
      </div>
    );

}

ChartPopup.propTypes ={
  /** Specifies the weather variable currently selected. */
  varCur: PropTypes.string,

  /** Sepecifies the location selected. */
  location: PropTypes.string,

  /** Specifies the start date for the data that is to be displayed. */
  fromDate: PropTypes.string,

  /** Specifies the end date for the data that is to be displayed. */
  toDate: PropTypes.string,

}

export default ChartPopup;


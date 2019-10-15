import React, {useState,useEffect} from 'react';
import {VictoryChart,VictoryLine,VictoryTheme,VictoryAxis,VictoryZoomContainer} from 'victory';
import {has} from 'lodash';
import PropTypes from 'prop-types';

import './App.css';
import {varMapping} from './Util';


const PERCENT="Percentage";
const CELSIUS="Celsius";

const PREVAL="precipitation.value";

/**
 * A component for displaying line graphs.
 * 
 * @component
 * 
 */
export default function ChartComponent(props) {

  const [yVal,setYVal]=useState(undefined);
  const [yLabel,setYLabel]=useState(undefined);


  useEffect(()=>{

    

      // 'Precipitation','Humidity','Wind Direction','Wind Speed','Cloudiness','Cloud Level','Dew Point','Pressure','Temperature'
      const variRequest=varMapping(props.varCur);

      if(variRequest === "precipitation"){
        if(props.minMax){
          if(props.index===0){
            setYVal("precipitation.minvalue");
          }else if(props.index===1){
            setYVal(PREVAL);
          }else{
            setYVal("precipitation.maxvalue");
          }
  
        }else{
          setYVal(PREVAL);
        }
        setYLabel("Millimetres");
  
      }else if(variRequest === "humidity"){
        setYVal(variRequest);
        setYLabel(PERCENT);
      }else if(variRequest === "windDirection"){
  
        // Displaying sin of the angle on the graph, rather than the angle/
        setYVal("windDirection.sinDeg");
        setYLabel("sin of angle");
      }else if(variRequest === "windSpeed"){
        setYVal("windSpeed.mps");
        setYLabel("Metres per second");
      }else if(variRequest === "cloudiness"){
        setYVal(variRequest);
        setYLabel(PERCENT);
      }else if(variRequest === "cloudLevel"){
        if(props.index===0){
          setYVal("cloud.low");
        }else if(props.index===1){
          setYVal("cloud.medium");
        }else{
          setYVal("cloud.high");
        }
        setYLabel(PERCENT);
      }else if(variRequest === "dewPoint"){
        setYVal(variRequest);
        setYLabel(CELSIUS);
      }else if(variRequest === "pressure"){
        setYVal(variRequest);
        setYLabel("Hectopascals");
      }else if(variRequest === "temperature"){
        setYVal(variRequest);
        setYLabel(CELSIUS);
      }
      
    
  },[props.varCur,props.minMax,props.index]);
    

    

    if(yVal&&props.data&&has(props.data[0],yVal)){

      return (

        <VictoryChart
          theme={VictoryTheme.material}
          scale={{ x: "time" }}
          containerComponent={
              <VictoryZoomContainer
                zoomDimension="x"
                zoomDomain={props.zoomDomain}
                onZoomDomainChange={props.handleZoom}
              />
            }
        >
          {props.data&&<VictoryLine
            style={{
              data: { stroke: "#c43a31" },
              parent: { border: "1px solid #ccc"}
            }}
            data={props.data}
            x="date"
            y={yVal}
          />}

          {props.data&&<VictoryAxis fixLabelOverlap={true}/>}
          {props.data&&<VictoryAxis dependentAxis label={yLabel} style={{ axisLabel: {padding: 45} }} tickFormat={
                  (x) => {if(x<.001){return x.toFixed(3);} if(x>999){return Math.round(x);} return x;}} />}

        </VictoryChart>

        );
      }else{

        return false;
      }

}


ChartComponent.propTypes ={
  /** Specifies the beginning and end times of the time domain of the x-axis. */
  zoomDomain: PropTypes.object,

  /** Called when there is a zoom domain change. */
  handleZoom: PropTypes.func,

  /** Data to be displayed on the graph. */
  data: PropTypes.array,

  /** The label for the y-axis. The x-axis is always time.  */
  yLabel: PropTypes.string,

  /** The name of the attribute in all objects of the data array to be plotted.*/
  yVal: PropTypes.string,
}



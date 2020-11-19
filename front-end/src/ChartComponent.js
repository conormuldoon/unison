import { has } from 'lodash';
import PropTypes from 'prop-types';
import React from 'react';
import { VictoryAxis, VictoryChart, VictoryLine, VictoryTheme, VictoryZoomContainer } from 'victory';
import './App.css';
import { varMapping } from './Util';



const PERCENT = "Percentage";
const CELSIUS = "Celsius";

const PREVAL = "precipitation.value";

/**
 * A component for displaying line graphs.
 * 
 * @component
 * 
 */
function ChartComponent({ curVar, minMax, index, data, zoomDomain, handleZoom }) {

  let yVal;
  let yLabel;


  // 'Precipitation','Humidity','Wind Direction','Wind Speed','Cloudiness','Cloud Level','Dew Point','Pressure','Temperature'
  const variRequest = varMapping(curVar);

  if (variRequest === "precipitation") {
    if (minMax) {
      if (index === 0) {
        yVal = "precipitation.minvalue";
      } else if (index === 1) {
        yVal = PREVAL;
      } else {
        yVal = "precipitation.maxvalue";
      }

    } else {
      yVal = PREVAL;
    }
    yLabel = "Millimetres";

  } else if (variRequest === "humidity") {
    yVal = variRequest;
    yLabel = PERCENT;
  } else if (variRequest === "windDirection") {

    // Displaying sin of the angle on the graph, rather than the angle/
    yVal = "windDirection.sinDeg";
    yLabel = "sin of angle";
  } else if (variRequest === "windSpeed") {
    yVal = "windSpeed.mps";
    yLabel = "Metres per second";
  } else if (variRequest === "cloudiness") {
    yVal = variRequest;
    yLabel = PERCENT;
  } else if(variRequest === "globalRadiation"){
    yVal=variRequest;
    yLabel = "Watts per meter squared";
  } else if (variRequest === "cloudLevel") {
    if (index === 0) {
      yVal = "cloud.low";
    } else if (index === 1) {
      yVal = "cloud.medium";
    } else {
      yVal = "cloud.high";
    }
    yLabel = PERCENT;
  } else if (variRequest === "dewPoint") {
    yVal = variRequest;
    yLabel = CELSIUS;
  } else if (variRequest === "pressure") {
    yVal = variRequest;
    yLabel = "Hectopascals";
  } else if (variRequest === "temperature") {
    yVal = variRequest;
    yLabel = CELSIUS;
  }


  if (has(data[0], yVal)) {

    return (

      <VictoryChart style={{ parent: { maxWidth: "55%", maxHeight: "55%" } }} responsive={true} padding={{ left: 70, bottom: 50, top: 10 }}
        domainPadding={{ x: [0, 0], y: 0 }}
        theme={VictoryTheme.material}
        scale={{ x: "time" }}
        containerComponent={
          <VictoryZoomContainer
            zoomDimension="x"
            zoomDomain={zoomDomain}
            onZoomDomainChange={handleZoom}
          />
        }
      >
        <VictoryLine
          style={{
            data: { stroke: "#c43a31" },
            parent: { border: "1px solid #ccc" }
          }}
          data={data}
          x="date"
          y={yVal}
        />

        <VictoryAxis fixLabelOverlap={true} />
        <VictoryAxis dependentAxis label={yLabel} style={{ axisLabel: { padding: 45 } }} tickFormat={
          (x) => { if (Math.abs(x) < .001 ) { return parseFloat(x.toFixed(3)); } if (Math.abs(x) > 999) { return Math.round(x); } return x; }} />

      </VictoryChart>

    );
  } else {

    return false;
  }

}


ChartComponent.propTypes = {
  /** Specifies the beginning and end times of the time domain of the x-axis. */
  zoomDomain: PropTypes.object,

  /** Called when there is a zoom domain change. */
  handleZoom: PropTypes.func.isRequired,

  /** Data to be displayed on the graph. */
  data: PropTypes.array.isRequired,


  /** The weather variable currently selected. */
  curVar: PropTypes.string.isRequired,

  /** True if the if displaying tabs for the precipitation variable.  */
  minMax: PropTypes.bool,

  /** The tab index - 0 for the left tab, 1 for the middle tab, and 2 for the right tab. */
  index: PropTypes.number,

}

export default ChartComponent;



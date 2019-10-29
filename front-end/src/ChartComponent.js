import { has } from 'lodash';
import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';
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
function ChartComponent(props) {

  const [yVal, setYVal] = useState(undefined);
  const [yLabel, setYLabel] = useState(undefined);


  useEffect(() => {



    // 'Precipitation','Humidity','Wind Direction','Wind Speed','Cloudiness','Cloud Level','Dew Point','Pressure','Temperature'
    const variRequest = varMapping(props.varCur);

    if (variRequest === "precipitation") {
      if (props.minMax) {
        if (props.index === 0) {
          setYVal("precipitation.minvalue");
        } else if (props.index === 1) {
          setYVal(PREVAL);
        } else {
          setYVal("precipitation.maxvalue");
        }

      } else {
        setYVal(PREVAL);
      }
      setYLabel("Millimetres");

    } else if (variRequest === "humidity") {
      setYVal(variRequest);
      setYLabel(PERCENT);
    } else if (variRequest === "windDirection") {

      // Displaying sin of the angle on the graph, rather than the angle/
      setYVal("windDirection.sinDeg");
      setYLabel("sin of angle");
    } else if (variRequest === "windSpeed") {
      setYVal("windSpeed.mps");
      setYLabel("Metres per second");
    } else if (variRequest === "cloudiness") {
      setYVal(variRequest);
      setYLabel(PERCENT);
    } else if (variRequest === "cloudLevel") {
      if (props.index === 0) {
        setYVal("cloud.low");
      } else if (props.index === 1) {
        setYVal("cloud.medium");
      } else {
        setYVal("cloud.high");
      }
      setYLabel(PERCENT);
    } else if (variRequest === "dewPoint") {
      setYVal(variRequest);
      setYLabel(CELSIUS);
    } else if (variRequest === "pressure") {
      setYVal(variRequest);
      setYLabel("Hectopascals");
    } else if (variRequest === "temperature") {
      setYVal(variRequest);
      setYLabel(CELSIUS);
    }


  }, [props.varCur, props.minMax, props.index]);




  if (yVal && props.data && has(props.data[0], yVal)) {

    return (

      <VictoryChart style={{ parent: { maxWidth: "55%", maxHeight: "55%" } }} responsive={true} padding={{ left: 70, bottom: 50, top: 10 }}
        domainPadding={{ x: [0, 0], y: 0 }}
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
        <VictoryLine
          style={{
            data: { stroke: "#c43a31" },
            parent: { border: "1px solid #ccc" }
          }}
          data={props.data}
          x="date"
          y={yVal}
        />

        <VictoryAxis fixLabelOverlap={true} />
        <VictoryAxis dependentAxis label={yLabel} style={{ axisLabel: { padding: 45 } }} tickFormat={
          (x) => { if (x < .001) { return x.toFixed(3); } if (x > 999) { return Math.round(x); } return x; }} />

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
  data: PropTypes.array,


  /** The weather variable currently selected. */
  varCur: PropTypes.string.isRequired,

  /** True if the if displaying tabs for the precipitation variable.  */
  minMax: PropTypes.bool,

  /** The tab index - 0 for the left tab, 1 for the middle tab, and 2 for the right tab. */
  index: PropTypes.number,

}

export default ChartComponent;



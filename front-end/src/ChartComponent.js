import React from 'react';
import {VictoryChart,VictoryLine,VictoryTheme,VictoryAxis,VictoryZoomContainer} from 'victory';

//import PropTypes from 'prop-types';

import './App.css';

/**
 * A component for displaying line graphs.
 * 
 * @param {*} props 
 */
export default function ChartComponent(props) {


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
          y={props.yVal}
        />}

        {props.data&&<VictoryAxis fixLabelOverlap={true}/>}
        {props.data&&<VictoryAxis dependentAxis label={props.yLabel} style={{ axisLabel: {padding: 45} }} tickFormat={
                (x) => {if(x<.001){return x.toFixed(3);} if(x>999){return Math.round(x);} return x;}} />}

      </VictoryChart>

    );

}


// ChartComponent.propTypes ={
//   /** Specifies the beginning and end times of the time domain of the x-axis. */
//   zoomDomain: PropTypes.object,

//   /** Called when there is a zoom domain change. */
//   handleZoom: PropTypes.func,

//   /** Data to be displayed on the graph. */
//   data: PropTypes.array,

//   /** The label for the y-axis. The x-axis is always time.  */
//   yLabel: PropTypes.string,

//   /** The name of the attribute in all objects of the data array to be plotted.*/
//   yVal: PropTypes.string,
// }



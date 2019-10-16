import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import React, { useState,useEffect  } from 'react';
import "react-tabs/style/react-tabs.css";
import ChartComponent from './ChartComponent';

import {LPER,MEDIAN,HPER,LOW,MEDIUM,HIGH,CL} from './Constant';

import PropTypes from 'prop-types';





/**
 * A component that displays a number of tabs for ChartComponents for the phenomenom being tracked. 
 * For exmaple, for cloud level, there is a tab for high, medium, and low level clouds. Otherwise,
 * only a single ChartComponent is displayed.
 *  
 * @component
 */
function TabsComponent(props){

  const [tabs,setTabs]=useState(undefined);


  useEffect(()=>{

    function updateTabs(){
      if(props.minMax){
  
        setTabs([LPER,MEDIAN,HPER]);
    
      }else if(props.varCur===CL){
    
        setTabs([LOW,MEDIUM,HIGH]);
    
      }else{
        setTabs(undefined);
      }
    }

    if(props.data&&props.data.length>0){

        updateTabs(props.minMax,props.varCur===CL);     

      }else{
        
        setTabs(undefined);  
        
      }
    },[props.data,props.varCur,props.minMax]
  );
  

      function tabsPanel(index){
        return (<TabPanel >
          <div id="tabdiv" data-testid='chart' >
            <ChartComponent data={props.data} zoomDomain={props.zoomDomain} index={index}
              handleZoom={props.setZoomDomain}
              varCur={props.varCur} minMax={props.minMax}  />
          </div>
        </TabPanel>
        );
      }

      return (<div  id="chartdiv">
          {tabs&&<Tabs id="tabscomponent" defaultIndex={1}>
          <TabList >
            <Tab>{tabs[0]}</Tab>
            <Tab>{tabs[1]}</Tab>
            <Tab>{tabs[2]}</Tab>
          </TabList>

          {tabsPanel(0)}
          {tabsPanel(1)}
          {tabsPanel(2)}

        </Tabs>}

        {!tabs&&<div data-testid='chart' id="singlevar">
          <ChartComponent data={props.data} zoomDomain={props.zoomDomain} handleZoom={props.setZoomDomain}
            varCur={props.varCur} minMax={props.minMax} />
          </div>
        }
      </div>
    );

}


TabsComponent.propTypes ={
 
  /** An array of data to be displayed on one or more charts. */
  data: PropTypes.array,

  /** The current weather variable selected. */
  varCur: PropTypes.string,

  /** Determines wheather to display tabs for the precipitation weather variable. This is needed in that with
   * some locations and models only provide a single preciption value (no minimum or maximum).
   */
  minMax: PropTypes.bool,

  /** Determines the range of data to be displayed. */
  zoomDomain: PropTypes.object,

  /** A function used to change the zoom domain. */
  setZoomDomain: PropTypes.func,

  
}

export default TabsComponent;

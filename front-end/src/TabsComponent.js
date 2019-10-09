import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import React, { useState,useEffect  } from 'react';
import "react-tabs/style/react-tabs.css";
import ChartComponent from './ChartComponent';

import {API,LPER,MEDIAN,HPER,LOW,MEDIUM,HIGH,CL,PRECIP} from './Constant';
import {varMapping} from './Util';


const PERCENT="Percentage";
const CELSIUS="Celsius";

const PREVAL="precipitation.value";

/**
 * A component that displays a number of tabs for ChartComponents for the phenomenom being tracked. 
 * For exmaple, for cloud level, there is a tab for high, medium, and low level clouds. Otherwise,
 * only a single tab is displayed.
 *  
 */
export default function TabsComponent(props){

  const [tabs,setTabs]=useState(undefined);

  const [yVal,setYVal]=useState(undefined);
  const [yLabel,setYLabel]=useState(undefined);


  useEffect(()=>{
    if(props.data&&props.data.length>0){

        const variRequest=varMapping(props.varCur);
        determineLabel(variRequest);
        if(props.minMax){
  
          setTabs([LPER,MEDIAN,HPER]);
      
        }else if(props.varCur===CL){
      
          setTabs([LOW,MEDIUM,HIGH]);
      
        }else if(tabs){
          setTabs(undefined);
        }
        

      }else{
        
        setTabs(undefined);
        setYVal(undefined);
        setYLabel(undefined);
        
        
      }
    },[props.data,props.varCur,props.minMax]
  );
  
    

  function determineLabel(variRequest){

    // 'Precipitation','Humidity','Wind Direction','Wind Speed','Cloudiness','Cloud Level','Dew Point','Pressure','Temperature'

    if(variRequest === "precipitation"){
      if(props.minMax){
        setYVal(["precipitation.minvalue",PREVAL,"precipitation.maxvalue"]);

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
      setYVal(["cloud.low","cloud.medium","cloud.high"])
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
    
  }

      function tabsPanel(index){
        return (<TabPanel >
          <div id="tabdiv" data-testid='chart' >
            <ChartComponent data={props.data} zoomDomain={props.zoomDomain} yVal={yVal[index]}
              handleZoom={props.setZoomDomain}
            yLabel={yLabel} />
          </div>
        </TabPanel>
        );
      }

      return (<div  id="chartdiv">
          {tabs&&yVal&&<Tabs id="tabscomponent" defaultIndex={1}>
          <TabList >
            <Tab>{tabs[0]}</Tab>
            <Tab>{tabs[1]}</Tab>
            <Tab>{tabs[2]}</Tab>
          </TabList>

          {tabsPanel(0)}
          {tabsPanel(1)}
          {tabsPanel(2)}

        </Tabs>}

        {yVal&&!tabs&&<div data-testid='chart' id="singlevar">
          <ChartComponent data={props.data} zoomDomain={props.zoomDomain} handleZoom={props.setZoomDomain}
            yVal={yVal} yLabel={yLabel} />
          </div>
        }
        {!yVal&&<div/>}
      </div>
    );

}

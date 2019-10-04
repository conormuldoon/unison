import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import React, { useState,useEffect  } from 'react';
import "react-tabs/style/react-tabs.css";
import ChartComponent from './ChartComponent';

import {API,LPER,MEDIAN,HPER,LOW,MEDIUM,HIGH,CL,PRECIP} from './Constant';
import {varMapping} from './Util';
const DLEN=16;

const PERCENT="Percentage";
const CELSIUS="Celsius";

const PREVAL="precipitation.value";

/**
 * A component that displays a number of tabs for ChartComponents for the phenomenom being tracked. 
 * For exmaple, for cloud level, there is a tab for high, medium, and low level clouds. Otherwise,
 * only a single tab is displayed.
 * 
 * @param {*} props 
 */
export default function TabsComponent(props){

  const [tabs,setTabs]=useState(undefined);

  const [data,setData]=useState(undefined);
  const [zoomDomain,setZoomDomain]=useState(undefined);
  const [yVal,setYVal]=useState(undefined);
  const [yLabel,setYLabel]=useState(undefined);

  
  useEffect(()=>{

    function updateTabs(minMax){


      if(minMax&&props.varCur===PRECIP){
  
        setTabs([LPER,MEDIAN,HPER]);
  
      }else if(props.varCur===CL){
  
        setTabs([LOW,MEDIUM,HIGH]);
  
      }else if(tabs){
        setTabs(undefined);
      }
  
    }
    
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

        let minMax=false;
        if(props.varCur === PRECIP&&n>0&&dataArray[0].precipitation.minvalue!==undefined){

          minMax=true;
        }
        determineLabel(minMax,variRequest);
        updateTabs(minMax);

        setData(dataArray);
        setZoomDomain({ x: [fd, td] });
      }else{

        setTabs(undefined);
        setYVal(undefined);
        setYLabel(undefined);
        setData(undefined);
        setZoomDomain(undefined);
      }
      
    }
    obtainData();
    

  },[props.varCur,props.location,props.fromDate,props.toDate,tabs]);



  function determineLabel(minMax,variRequest){

    // 'Precipitation','Humidity','Wind Direction','Wind Speed','Cloudiness','Cloud Level','Dew Point','Pressure','Temperature'

    if(variRequest === "precipitation"){
      if(minMax){
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
      <div id="tabdiv">
        <ChartComponent data={data} zoomDomain={zoomDomain} yVal={yVal[index]}
          handleZoom={setZoomDomain}
         yLabel={yLabel} />
      </div>
    </TabPanel>);
  }





      return (<div data-testid='chart' id="chartdiv">
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

        {!tabs&&<div data-testid='chart' id="singlevar">
          <ChartComponent data={data} zoomDomain={zoomDomain} handleZoom={setZoomDomain}
            yVal={yVal} yLabel={yLabel} />
          </div>
        }
      </div>
    );

}

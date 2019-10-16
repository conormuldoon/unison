

import React, { Component } from 'react';

import './App.css';

import 'react-day-picker/lib/style.css';

import DateSelector from './DateSelector';

import ARLocationComponent from './ARLocationComponent';

import LeafletMap from './LeafletMap';
import {FORMAT,API} from './Constant';
import {TODAY,FROM_DATE} from './Util';

import  {
  formatDate
} from 'react-day-picker/moment';

let maxLen;
const WSPACE='\u00a0';
const varOpt=['Precipitation','Humidity','Wind Direction','Wind Speed','Cloudiness','Cloud Level','Dew Point','Pressure','Temperature'];
    
/**
 * Application component for Unison. Once mounted, it connects to the back-end to receive a list of the locations being tracked.
 * 
 * @component
 * 
 */
class Unison extends Component {


  constructor(props) {
    super(props);

    this.state = {
      fromDate: FROM_DATE,
      toDate: TODAY,
      option: undefined,
      marker: undefined,
      clickedLocation: undefined,
      curVar: 'Precipitation',
      curLoc: undefined,
    };
  }

  componentDidMount = () => {
    this.cancelObtainData=this.obtainData();
  }

  componentWillUnmount = () => {
    this.cancelObtainData();
  }

  obtainData = () => {

    let active=true;

    new Promise(async () =>{
      let response = await fetch(API+'/location');

      if(response.ok){
        let locationArray = await response.json();

        if(active){
          let n=locationArray.length;

          let newOption=[];
          let newMarker=[];

          maxLen=0;
          for(let i=0;i<n;i++){
            maxLen=Math.max(locationArray[i].name.length)

          }
          for(let i=0;i<n;i++){
            newOption.push(this.addPadding(locationArray[i].name));
            let pos=[locationArray[i].geom.coordinates[1],locationArray[i].geom.coordinates[0]];

            newMarker.push({name:locationArray[i].name,position:pos});


          }
          if(n>0){
            this.setState({option:newOption,marker:newMarker,curLoc: this.addPadding(locationArray[0].name) });
          }else{
            this.setState({curLoc: undefined,option:undefined,marker:undefined});
          }
        }
      }
    });

    const cancel = () => active = false;
    return cancel;

  }

  markerClicked = (location) => {

    let newLoc=this.addPadding(location,maxLen);
    this.setState({clickedLocation:newLoc,curLoc:newLoc});
  }

  _onLocationSelect = (event) =>{

    this.setState({curLoc: event.target.value});
  }

  _onVarSelect = (event) =>{
    this.setState({curVar: event.target.value});
  }

  removePadding = (location) =>{

    if(location!==undefined){

      const n=location.length;
      for(let i=0;i<n;i++){
        if(location.charAt(i)===WSPACE){
          if(i===n-1||location.charAt(i+1)===WSPACE){
              return location.substring(0,i);
          }
        }

      }
      
    }
    return location;
  }

  addPadding = (location) =>{
    let num=maxLen-location.length;
    let newLoc=location;

    for(let i=0;i<num;i++){
      newLoc=newLoc.concat(WSPACE);
    }

    return newLoc;
  }

  handleStartChange = (selectedDate) => {
    this.setState({ fromDate: formatDate(selectedDate,FORMAT) });
  }

  handleEndChange = (selectedDate) => {
    this.setState({ toDate: formatDate(selectedDate,FORMAT) });
  }


  test = (event) =>{
    alert('hello '+event.target.value);
  }

  render() {


    return (


      <div id="mapdiv">

      <div id="logos">

          <center>
            <img id="logoitem" alt="" src={this.props.logoLeft} />


            <img id="logoitem" alt="" src={this.props.logoRight} />
          </center>

      </div>

      <LeafletMap marker={this.state.marker} curVar={this.state.curVar} curLoc={this.state.curLoc} 
          markerCallback={this.markerClicked} fromDate={this.state.fromDate} toDate={this.state.toDate} 
          mapCentre={this.props.mapCentre}
          
          />
      <div id="selectdiv" >

        <center>

          <div>
            <div id='variDD' >

                <select disabled={!this.state.curLoc} onChange={this._onVarSelect}>

                    {varOpt.map((opt) => <option key={opt} value={opt}>{opt}</option>)}

                </select>

            </div>

            <div className='marginItem' >

                <select disabled={!this.state.curLoc} onChange={this._onLocationSelect} defaultValue="Location" value={this.state.curLoc}>
                    {!this.state.option&&<option key="Location" value="Location" >Location</option>}
                    {this.state.option&&this.state.option.map((opt) => <option key={opt} value={opt}>{opt}</option>)}

                </select>

              
            </div>


            <div className='pLeft' >
              <DateSelector 
                label="From date" dateValue={this.state.fromDate}
                handleDayChange={this.handleStartChange}
              />
            </div>

            <div className='pLeft' >
              <DateSelector
                label="To date" dateValue={this.state.toDate}
                handleDayChange={this.handleEndChange}
              />

            </div>

          



            <a className='pLeft'  href={API+'/csv'+this.state.curVar.replace(/ /g,'')+'?location='+this.removePadding(this.state.curLoc)+'&fromDate='+this.state.fromDate+'&toDate='+this.state.toDate}>

              <button disabled={!this.state.curLoc} >
                CSV
              </button>

            </a>
          </div>

          <ARLocationComponent obtainData={this.obtainData} location={this.removePadding(this.state.curLoc)}/>

        </center>

      </div>



      </div>
    );
  }
}

export default Unison;

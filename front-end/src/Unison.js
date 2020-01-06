

import PropTypes from 'prop-types';
import React, { Component } from 'react';
import 'react-day-picker/lib/style.css';
import { formatDate } from 'react-day-picker/moment';
import './App.css';
import ARLocationComponent from './ARLocationComponent';
import { API, FORMAT } from './Constant';
import DateSelector from './DateSelector';
import LeafletMap from './LeafletMap';
import { today, tomorrow } from './Util';


const varOpt = ['Precipitation', 'Humidity', 'Wind Direction', 'Wind Speed', 'Cloudiness', 'Cloud Level', 'Dew Point', 'Pressure', 'Temperature'];

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
      fromDate: today(),
      toDate: tomorrow(),
      option: undefined,
      marker: undefined,
      curVar: varOpt[0],
      curLoc: undefined,
    };


  }

  componentDidMount = () => {
    this.cancelObtainData = this.obtainData();
  }

  componentWillUnmount = () => {
    this.cancelObtainData();
  }

  obtainData = () => {

    let active = true;

    async function requestLocation(comp) {
      let response = await fetch(API + '/location');

      if (response.ok) {
        let locationArray = await response.json();

        if (active) {
          let n = locationArray.length;

          let newOption = [];
          let newMarker = [];

          for (let i = 0; i < n; i++) {
            newOption.push(locationArray[i].name);
            let pos = [locationArray[i].geom.coordinates[1], locationArray[i].geom.coordinates[0]];

            newMarker.push({ name: locationArray[i].name, position: pos });


          }
          if (n > 0) {
            comp.setState({ option: newOption, marker: newMarker, curLoc: locationArray[0].name });
          } else {
            comp.setState({ curLoc: undefined, option: undefined, marker: undefined });
          }
        }
      }
    }

    requestLocation(this);

    const cancel = () => active = false;
    return cancel;

  }

  markerClicked = (location) => {

    this.setState({ curLoc: location });
  }

  _onLocationSelect = (event) => {

    this.setState({ curLoc: event.target.value });
  }

  _onVarSelect = (event) => {
    this.setState({ curVar: event.target.value });
  }



  handleStartChange = (selectedDate) => {
    this.setState({ fromDate: formatDate(selectedDate, FORMAT) });
  }

  handleEndChange = (selectedDate) => {
    this.setState({ toDate: formatDate(selectedDate, FORMAT) });
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
                  {!this.state.option && <option key="Location" value="Location" >Location</option>}
                  {this.state.option && this.state.option.map((opt) => <option key={opt} value={opt}>{opt}</option>)}

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





              <a className='pLeft' href={API + '/csv' + this.state.curVar.replace(/ /g, '') + '_' + Date.now() + '.csv?location=' + this.state.curLoc + '&fromDate=' + this.state.fromDate + '&toDate=' + this.state.toDate}>

                <button disabled={!this.state.curLoc} >
                  CSV
              </button>

              </a>
            </div>

            <ARLocationComponent obtainData={this.obtainData} location={this.state.curLoc} />

          </center>

        </div>



      </div>
    );
  }
}

Unison.propTypes = {

  /** The latitude/longitude coordinates for the centre of the map. */
  mapCentre: PropTypes.array.isRequired,

  /** A logo displayed at the bottom of the screen. It will be displayed to the left
   * if the logoRight prop is defined.
   */
  logoLeft: PropTypes.any,

  /** A logo displayed at the bottom of the screen. It will be displayed to the right
   * if the logoLeft prop is defined.
   */
  logoRight: PropTypes.any,

}

export default Unison;

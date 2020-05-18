

import PropTypes from 'prop-types';
import React, { useState, useEffect } from 'react';
import 'react-day-picker/lib/style.css';
import { formatDate } from 'react-day-picker/moment';
import './App.css';
import ARLocationComponent from './ARLocationComponent';
import { API, FORMAT, VAR_OPT } from './Constant';
import DateSelector from './DateSelector';
import LeafletMap from './LeafletMap';
import { today, tomorrow, expandLink } from './Util';

/**
 * Application component for Unison. Once mounted, it connects to the back-end to receive a list of the locations being tracked.
 * 
 * @component
 * 
 */
function Unison(props) {


  const [fromDate, setFromDate] = useState(today());
  const [toDate, setToDate] = useState(tomorrow());
  const [option, setOption] = useState(undefined);
  const [marker, setMarker] = useState(undefined);
  const [curLoc, setCurLoc] = useState(undefined);
  const [curVar, setCurVar] = useState(VAR_OPT[0]);
  const [featureProperties, setFeatureProperties] = useState(undefined);

  const obtainData = () => {
    let active = true;

    async function requestLocation() {
      let response = await fetch(API + '/location');

      if (active && response.ok) {
        const fc = await response.json();
        const map = new Map();


        const locationArray = fc.features;

        if (active) {
          let n = locationArray.length;

          let newOption = [];
          let newMarker = [];

          for (let i = 0; i < n; i++) {
            newOption.push(locationArray[i].properties.name);
            let pos = [locationArray[i].geometry.coordinates[1], locationArray[i].geometry.coordinates[0]];

            const properties = locationArray[i].properties;
            newMarker.push({ name: properties.name, position: pos });
            map.set(properties.name, properties);


          }
          if (n > 0) {
            setFeatureProperties(map);
            setOption(newOption);
            setMarker(newMarker);
            setCurLoc(locationArray[0].properties);

          } else {
            setFeatureProperties(undefined);
            setOption(undefined);
            setMarker(undefined);
            setCurLoc(undefined);
          }
        }
      }
    }

    requestLocation();

    const cancel = () => active = false;
    return cancel;
  };

  useEffect(() => {

    return obtainData();
  }, []);


  const updateCurLoc = (locationName) => {
    setCurLoc(featureProperties.get(locationName));
  }

  const markerClicked = (location) => {
    updateCurLoc(location);
  }

  const _onLocationSelect = (event) => {

    updateCurLoc(event.target.value);

  }

  const _onVarSelect = (event) => {
    setCurVar(event.target.value);

  }

  const handleStartChange = (selectedDate) => {
    setFromDate(formatDate(selectedDate, FORMAT));

  }

  const handleEndChange = (selectedDate) => {

    setToDate(formatDate(selectedDate, FORMAT));

  }

  const dashDate = (date) => {

    return date.replace(/\//g, '-');
  }

  const spaceToUnderscore = (s) => {
    return s.replace(/ /g, '_')
  }

  return (

    <div id="mapdiv">

      <div id="logos">

        <center>
          <img id="logoitem" alt="" src={props.logoLeft} />


          <img id="logoitem" alt="" src={props.logoRight} />
        </center>

      </div>

      <LeafletMap marker={marker} curVar={curVar} location={curLoc} featureProperties={featureProperties}
        markerCallback={markerClicked} fromDate={fromDate} toDate={toDate}
        mapCentre={props.mapCentre}

      />
      <div id="selectdiv" >

        <center>

          <div>
            <div id='variDD' >

              <select disabled={!curLoc} onChange={_onVarSelect}>

                {VAR_OPT.map((opt) => <option key={opt} value={opt}>{opt}</option>)}

              </select>

            </div>

            <div className='marginItem' >

              {curLoc && <select onChange={_onLocationSelect} value={curLoc.name}>
                {!option && <option key="Location" value="Location" >Location</option>}
                {option && option.map((opt) => <option key={opt} value={opt}>{opt}</option>)}

              </select>}


            </div>


            <div className='pLeft' >
              <DateSelector
                label="From date" dateValue={fromDate}
                handleDayChange={handleStartChange}
              />
            </div>

            <div className='pLeft' >
              <DateSelector
                label="To date" dateValue={toDate}
                handleDayChange={handleEndChange}
              />

            </div>

            {curLoc && <a download={spaceToUnderscore(curLoc.name) + '_' + spaceToUnderscore(curVar) + '_' + dashDate(fromDate) + "_" + dashDate(toDate) + '.csv'}
              className='pLeft'
              href={expandLink(curLoc, curVar, fromDate, toDate)}>

              <button disabled={!curLoc} >
                CSV
              </button>

            </a>}
          </div>

          <ARLocationComponent obtainData={obtainData} location={curLoc} featureProperties={featureProperties} />

        </center>

      </div>



    </div>
  );

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

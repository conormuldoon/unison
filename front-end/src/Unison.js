

import PropTypes from 'prop-types';
import React, { useState, useEffect } from 'react';
import 'react-day-picker/lib/style.css';
import { formatDate } from 'react-day-picker/moment';
import './App.css';
import ARLocationComponent from './ARLocationComponent';
import { FORMAT, EXCLUDE_REL } from './Constant';
import DateSelector from './DateSelector';
import LeafletMap from './LeafletMap';
import { today, tomorrow, expandLink, problemConnecting } from './Util';
import HttpStatus from 'http-status-codes';

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
  const [curVar, setCurVar] = useState(undefined);
  const [varOpt, setVarOpt] = useState(undefined);
  const [featureProperties, setFeatureProperties] = useState(undefined);
  const [linksProperty, setLinksProperty] = useState(undefined);


  const obtainData = () => {
    let active = true;

    const halGetHeader = {
      method: 'GET',
      headers: new Headers({
        'Accept': 'application/hal+json'
      })
    }

    async function requestLocation(uri) {

      const response = await fetch(uri, halGetHeader);

      console.log(uri);
      console.log(response);

      if (active && response.ok) {
        const model = await response.json();

        if (active) {
          const map = new Map();

          const fc = model.content;


          const locationArray = fc.features;


          let n = locationArray.length;

          const newOption = [];
          const newMarker = [];

          for (let i = 0; i < n; i++) {
            newOption.push(locationArray[i].properties.name);
            let pos = [locationArray[i].geometry.coordinates[1], locationArray[i].geometry.coordinates[0]];

            const properties = locationArray[i].properties;
            newMarker.push({ name: properties.name, position: pos });
            map.set(properties.name, properties);


          }

          setFeatureProperties(map);

          if (n > 0) {


            setOption(newOption);
            setMarker(newMarker);
            setCurLoc(locationArray[0].properties);

          } else {

            setOption(undefined);
            setMarker(undefined);
            setCurLoc(undefined);

          }

          const varOpt = [];

          for (const rel in model._links) {
            if (EXCLUDE_REL.includes(rel)) {
              continue;
            }

            const s = rel.charAt(0).toUpperCase() + rel.substring(1);
            let optName = '';
            let sw = 0;
            for (let i = 1; i < s.length; i++) {
              const c = s.charAt(i);
              if (c >= 'A' && c <= 'Z') {
                optName += s.substring(sw, i) + ' ';
                sw = i;
              }
            }
            optName += s.substring(sw);
            varOpt.push(optName);
          }

          if (varOpt.length > 0) {
            setCurVar(varOpt[0]);
            setVarOpt(varOpt)
          } else {
            alert("No weather data is associated with the collection.")
          }

          setLinksProperty(model._links);

        }

      }
    }

    async function requestModel() {

      const response = await fetch('/', halGetHeader);

      if (active && response.ok) {
        const model = await response.json();

        if (active)
          requestLocation(model._links.locationCollection.href);
      } else if (response.status === HttpStatus.GATEWAY_TIMEOUT) {
        problemConnecting();
      }
    }

    requestModel();

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
        mapCentre={props.mapCentre} linksProperty={linksProperty} />

      {linksProperty && <div id="selectdiv" >

        <center>

          <div>
            <div id='variDD' >

              {varOpt && <select onChange={_onVarSelect}>

                {varOpt.map((opt) => <option key={opt} value={opt}>{opt}</option>)}

              </select>}

            </div>

            {curLoc && <div className='marginItem' >

              <select onChange={_onLocationSelect} value={curLoc.name}>
                {!option && <option key="Location" value="Location" >Location</option>}
                {option && option.map((opt) => <option key={opt} value={opt}>{opt}</option>)}

              </select>

            </div>}


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

            {curLoc && curVar && <a download={spaceToUnderscore(curLoc.name) + '_' + spaceToUnderscore(curVar) + '_' + dashDate(fromDate) + "_" + dashDate(toDate) + '.csv'}
              className='pLeft'
              href={expandLink(linksProperty, curLoc, curVar, fromDate, toDate)}>

              <button>
                CSV
              </button>

            </a>}
          </div>

          <ARLocationComponent obtainData={obtainData} location={curLoc} featureProperties={featureProperties} linksProperty={linksProperty} />

        </center>

      </div>}



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

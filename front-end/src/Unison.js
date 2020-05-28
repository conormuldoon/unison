

import PropTypes from 'prop-types';
import React, { useState, useEffect } from 'react';
import 'react-day-picker/lib/style.css';
import { formatDate } from 'react-day-picker/moment';
import './App.css';
import ARLocationComponent from './ARLocationComponent';
import { FORMAT, SELF } from './Constant';
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


  const obtainData = () => {
    let active = true;

    async function requestFeatureCollection(uri) {
      const response = await fetch(uri, {
        method: 'GET',
        headers: new Headers({
          'Accept': 'application/geo+json'
        })
      });

      if (active && response.ok) {
        const fc = await response.json();

        if (active) {
          const locationArray = fc.features;
          const n = locationArray.length;

          const newOption = [];
          const newMarker = [];

          for (let i = 0; i < n; i++) {
            newOption.push(locationArray[i].properties.name);
            let pos = [locationArray[i].geometry.coordinates[1], locationArray[i].geometry.coordinates[0]];

            const properties = locationArray[i].properties;
            newMarker.push({ name: properties.name, position: pos });

          }

          if (n > 0) {

            setOption(newOption);
            setMarker(newMarker);
            return true;

          }
        }


      }

      return false;

    }

    const halGetHeader = {
      method: 'GET',
      headers: new Headers({
        'Accept': 'application/hal+json'
      })
    }

    async function requestCollectionHAL(uri) {

      const response = await fetch(uri, halGetHeader);

      if (active && response.ok) {
        const model = await response.json();
        if (active) {
          const map = new Map();
          if (model._embedded) {
            const list = model._embedded.locationModelList;
            const n = list.length;
            for (let i = 0; i < n; i++) {
              map.set(list[i].name, list[i]);
            }

            const locationData = await requestFeatureCollection(uri);
            if (active && locationData) {
              setFeatureProperties(map);

              const varOpt = [];

              if (n > 0) {

                for (const rel in list[0]._links) {
                  if (SELF === rel) {
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
              }
              if (varOpt.length > 0) {
                setCurLoc(list[0]);
                setCurVar(varOpt[0]);
                setVarOpt(varOpt);
                return;

              } else {
                alert("No weather data is associated with the collection.")
              }


            }
          }
        }
      }

      setCurVar(undefined);
      setVarOpt(undefined);
      setOption(undefined);
      setMarker(undefined);
      setCurLoc(undefined);
    }

    async function requestModel() {

      const response = await fetch('/', halGetHeader);

      if (active && response.ok) {
        const model = await response.json();

        if (active)
          requestCollectionHAL(model._links.locationCollection.href);
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


  const handleCSV = async () => {

    const response = await fetch(expandLink(curLoc, curVar, fromDate, toDate));

    if (response.ok) {
      const blob = await response.blob();
      const a = document.createElement('a');
      a.href = window.URL.createObjectURL(blob);
      a.download = spaceToUnderscore(curLoc.name) + '_' + spaceToUnderscore(curVar) + '_'
        + dashDate(fromDate) + "_" + dashDate(toDate) + '.csv';
      a.click();
    } else if (response.status === HttpStatus.GATEWAY_TIMEOUT) {
      problemConnecting();
    }
  }

  return (

    <div id="mapdiv">

      <div id="logos">

        <center>
          <img id="logoitem" alt="" src={props.logoLeft} />


          <img id="logoitem" alt="" src={props.logoRight} />
        </center>

      </div>

      <LeafletMap marker={marker} curVar={curVar} featureProperties={featureProperties}
        markerCallback={markerClicked} fromDate={fromDate} toDate={toDate}
        mapCentre={props.mapCentre} linksProperty={curLoc} />

      <div id="selectdiv" >

        <center>

          <div>
            {curLoc && <div id='variDD' >

              {varOpt && <select onChange={_onVarSelect}>

                {varOpt.map((opt) => <option key={opt} value={opt}>{opt}</option>)}

              </select>}

            </div>}

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

            {curLoc && curVar && <div className='pLeft'> <button onClick={handleCSV}>CSV</button> </div>}
          </div>

          <ARLocationComponent obtainData={obtainData} linksProperty={curLoc} />

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

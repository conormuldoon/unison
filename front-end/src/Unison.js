

import PropTypes from 'prop-types';
import React, { useState, useEffect } from 'react';
import 'react-day-picker/lib/style.css';
import { formatDate } from 'react-day-picker/moment';
import './App.css';
import ARLocationComponent from './ARLocationComponent';
import { FORMAT, SELF } from './Constant';
import createSelector from './selectorFactory';
import { today, tomorrow, expandLink, problemConnecting } from './Util';
import HttpStatus from 'http-status-codes';

import { createLocationFactory, createRemoveFactory } from './closureFactory';

/**
 * Application component for Unison. Once mounted, it connects to the back-end to receive a list of the locations being tracked.
 * 
 * @component
 * 
 */
function Unison(props) {


  const [fromDate, setFromDate] = useState(today());
  const [toDate, setToDate] = useState(tomorrow());
  const [option, setOption] = useState(null);
  const [marker, setMarker] = useState(null);
  const [curLoc, setCurLoc] = useState(null);
  const [curVar, setCurVar] = useState(null);
  const [varOpt, setVarOpt] = useState(null);
  const [locationMap, setLocationMap] = useState(null);
  const [collectionModel, setCollectionModel] = useState(null);

  const obtainData = () => {

    class ResponseError extends Error {
      constructor(message, status) {
        super(message);
        this.status = status;
      }
    }

    function checkResponse(response, endpoint) {
      if (!response.ok)
        throw new ResponseError('Bad response for ' + endpoint, response.status);
    }

    async function requestFeatureCollection(uri) {
      const response = await fetch(uri, {
        method: 'GET',
        headers: new Headers({
          'Accept': 'application/geo+json'
        })
      });


      checkResponse(response, uri);

      const fc = await response.json();

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


      } else {
        setOption(null);
        setMarker(null);
      }


    }

    const halGetHeader = {
      method: 'GET',
      headers: new Headers({
        'Accept': 'application/hal+json'
      }),

    }

    function clearCurrent() {
      setCurLoc(null);
      setCurVar(null);
      setVarOpt(null);
      setLocationMap(null);

    }

    async function requestCollectionHAL(uri) {

      const response = await fetch(uri, halGetHeader);


      checkResponse(response, uri);

      const model = await response.json();


      setCollectionModel(model);

      if (model._embedded) {
        const list = model._embedded.locationModelList;
        const n = list.length;
        const map = new Map();
        for (let i = 0; i < n; i++) {
          map.set(list[i].name, list[i]);
        }

        setLocationMap(map);

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

          clearCurrent();
          alert("No weather data is associated with the collection.")
        }


      } else {
        clearCurrent();
      }


    }

    async function requestModel() {
      try {

        const root = process.env.PUBLIC_URL;

        const response = await fetch(root, halGetHeader);

        checkResponse(response, root);


        const model = await response.json();

        const uri = model._links.locationCollection.href;
        await requestCollectionHAL(uri);
        await requestFeatureCollection(uri);

      } catch (e) {
        if (e instanceof ResponseError) {
          console.error("HTTP status code: " + e.status);

        }
        problemConnecting();

      }

    }


    requestModel();




  };

  useEffect(() => {

    obtainData();
  }, []);


  const updateCurLoc = (locationName) => {
    setCurLoc(locationMap.get(locationName));
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
        + fromDate + "_" + toDate + '.csv';
      a.click();
    } else if (response.status === HttpStatus.GATEWAY_TIMEOUT) {
      problemConnecting();
    }
  }

  return (

    <div id="mapdiv">

      <div id="logos">

        <center>
          {props.logoLeft}
          {props.logoRight}
        </center>

      </div>

      {props.createMap(marker, curVar, locationMap, markerClicked, fromDate, toDate, curLoc)}

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


            <div className='pLeft' >{
              createSelector("From date", fromDate, handleStartChange)
            }
            </div>

            <div className='pLeft' >
              {
                createSelector("To date", toDate, handleEndChange)
              }
            </div>

            {curLoc && curVar && <div className='pLeft'> <button onClick={handleCSV}>CSV</button> </div>}
          </div>

          <ARLocationComponent createLocation={createLocationFactory(obtainData, collectionModel)}
            createRemove={curLoc ? createRemoveFactory(obtainData, curLoc._links[SELF].href, curLoc.name) : null} />

        </center>

      </div>



    </div>
  );

}

Unison.propTypes = {

  /** A function for creating the map. */
  createMap: PropTypes.func.isRequired,

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

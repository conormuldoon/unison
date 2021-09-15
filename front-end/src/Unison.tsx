
import React, { useState, useEffect } from 'react';
import 'react-day-picker/lib/style.css';
import { formatDate } from 'react-day-picker/moment';
import './App.css';
import ARLocationComponent from './ARLocationComponent';
import { FORMAT, SELF } from './Constant';
import DateSelector from './DateSelector';
import { today, tomorrow, problemConnecting, varMapping } from './Util';
import HttpStatus from 'http-status-codes';

import { MapMarker } from './LeafletMap';

import { createLocationFactory } from './LocationForm';

import { createRemoveFactory } from './RemoveComponent';
import { createPopupFactory } from './ChartPopup';
import parser from 'uri-template';


interface UnisonProps {

  createMap: (marker: MapMarker[] | null,
    markerCallback: (locationName: string) => void,
    popupFactory?: (closePopup: () => void) => React.ReactNode) => JSX.Element | undefined;

  logoLeft?: React.ReactNode;
  logoRight?: React.ReactNode;
}


type ModelLink = {
  "self": string,
  "contains": string

}



class ResponseError extends Error {

  private status: number;

  constructor(message: string, status: number) {
    super(message);
    this.status = status;
  }

  logStatus() {

    console.error("HTTP status code: " + status);

  }
}

function checkResponse(ok: boolean, endpoint: string, status: number) {
  if (!ok)
    throw new ResponseError('Bad response for ' + endpoint, status);
}

const OMIT = 'omit';

function jsonHeader(accept: 'geo' | 'hal'): RequestInit {
  return {
    credentials: OMIT,
    headers: new Headers({
      'Accept': 'application/' + accept + '+json'
    })
  }
}

const halGetHeader: RequestInit = jsonHeader('hal');

type Link = {
  [key: string]: {
    href: string
  };
}

function createLinkMap(listItem: Link) {
  const itemMap = new Map<string, string>();
  for (const rel in listItem) {
    itemMap.set(rel, listItem[rel].href);

  }
  return itemMap;
}

const spaceToUnderscore = (s: string) => {
  return s.replace(/ /g, '_')
}

function optionName(s: string) {
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
  return optName;
}

function variableOptions(vo: string[], link: Link) {
  for (const rel in link) {
    if (SELF === rel) {
      continue;
    }

    const s = rel.charAt(0).toUpperCase() + rel.substring(1);
    const optName = optionName(s);
    vo.push(optName);
  }
}

type LinkMap = Map<string, string>;


/**
 * Application component for Unison. Once mounted, it connects to the back-end to receive a list of the locations being tracked.
 * 
 * @component
 * 
 */
function Unison({ createMap, logoLeft, logoRight }: UnisonProps): JSX.Element {


  const [fromDate, setFromDate] = useState(today());
  const [toDate, setToDate] = useState(tomorrow());
  const [option, setOption] = useState<string[] | null>(null);
  const [marker, setMarker] = useState<MapMarker[] | null>(null);
  const [curLoc, setCurLoc] = useState<string | null>(null);
  const [curVar, setCurVar] = useState<string | null>(null);
  const [varOpt, setVarOpt] = useState<string[] | null>(null);
  const [locationMap, setLocationMap] = useState<Map<string, LinkMap> | null>(null);
  const [modelLink, setModelLink] = useState<ModelLink | null>(null);

  async function requestFeatureCollection(uri: string) {
    const response = await fetch(uri, jsonHeader('geo'));


    checkResponse(response.ok, uri, response.status);

    const fc = await response.json();

    const locationArray = fc.features;
    const n = locationArray.length;

    const newOption = [];
    const newMarker: MapMarker[] = [];

    for (let i = 0; i < n; i++) {
      newOption.push(locationArray[i].properties.name);
      const pos: [number, number] = [locationArray[i].geometry.coordinates[1], locationArray[i].geometry.coordinates[0]];

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

  function clearCurrent() {

    setCurLoc(null);
    setCurVar(null);
    setVarOpt(null);
    setLocationMap(null);

  }

  function processModelList(list: [{ name: string, _links: Link }]) {

    const n = list.length;
    const map = new Map();

    for (let i = 0; i < n; i++) {

      map.set(list[i].name, createLinkMap(list[i]._links));
    }

    setLocationMap(map);

    const vo: string[] = [];

    if (n > 0) {
      variableOptions(vo, list[0]._links);

    }

    if (vo.length > 0) {
      setCurLoc(list[0].name);
      setCurVar(vo[0]);
      setVarOpt(vo);

    } else {

      clearCurrent();
      alert("No weather data is associated with the collection.")
    }
  }

  async function requestCollectionHAL(uri: string) {

    const response = await fetch(uri, halGetHeader);


    checkResponse(response.ok, uri, response.status);

    const model = await response.json();


    setModelLink({ self: model._links.self.href, contains: model._links.contains.href });


    if (model._embedded) {
      processModelList(model._embedded.locationModelList);
    } else {
      clearCurrent();
    }


  }

  const obtainData = () => {


    async function requestModel() {
      try {

        const root = process.env.PUBLIC_URL;

        const response = await fetch(root, jsonHeader('hal'));

        checkResponse(response.ok, root, response.status);
        const model = await response.json();
        const uri = model._links.locationCollection.href;
        await requestCollectionHAL(uri);
        await requestFeatureCollection(uri);

      } catch (e) {
        if (e instanceof ResponseError) {
          e.logStatus();

        } else {
          console.log(e);
        }
        problemConnecting();


      }

    }

    requestModel();




  };

  useEffect(obtainData, []);

  const markerClicked = (location: string) => {
    setCurLoc(location);

  }

  const _onLocationSelect = (event: React.ChangeEvent<HTMLSelectElement>) => {

    setCurLoc(event.target.value);

  }

  const _onVarSelect = (event: React.ChangeEvent<HTMLSelectElement>) => {

    setCurVar(event.target.value);

  }

  const handleStartChange = (selectedDate: Date) => {
    setFromDate(formatDate(selectedDate, FORMAT));

  }

  const handleEndChange = (selectedDate: Date) => {

    setToDate(formatDate(selectedDate, FORMAT));

  }



  function expandLink() {

    if (locationMap && curLoc && curVar) {
      const linkMap = locationMap.get(curLoc);
      if (linkMap) {
        const href = linkMap.get(varMapping(curVar));
        const template = parser.parse(href);
        return template.expand({ name: curLoc, fromDate: fromDate, toDate: toDate });
      }
    }


  }

  const handleCSV = async () => {


    const response = await fetch(expandLink(), {
      credentials: OMIT
    });

    if (response.ok && curVar && curLoc) {
      const blob = await response.blob();
      const a = document.createElement('a');
      a.href = window.URL.createObjectURL(blob);
      a.download = spaceToUnderscore(curLoc) + '_' + spaceToUnderscore(curVar) + '_'
        + fromDate + "_" + toDate + '.csv';
      a.click();
    } else if (response.status === HttpStatus.GATEWAY_TIMEOUT) {
      problemConnecting();
    }
  }


  function createSelector(label: string, dateValue: Date, handleDayChange: (day: Date) => void) {
    return <DateSelector label={label} dateValue={dateValue} handleDayChange={handleDayChange} />;
  }

  let popupFactory;
  if (curLoc && curVar) {
    const uri = expandLink();
    popupFactory = createPopupFactory(uri, curVar, curLoc);
  }

  let arc = null;
  if (modelLink) {
    let createRemove = null;


    if (curLoc && locationMap) {

      const linkMap = locationMap.get(curLoc);
      if (linkMap) {
        createRemove = createRemoveFactory(obtainData, curLoc, linkMap.get(SELF));
      }
    }
    arc = <ARLocationComponent createLocation={createLocationFactory(obtainData, modelLink.self, modelLink.contains)}
      createRemove={createRemove} />
  }

  return (

    <div id="mapdiv">

      <div id="logos" style={{ textAlign: "center" }} >

        {logoLeft}
        {logoRight}


      </div>

      {createMap(marker, markerClicked, popupFactory)}

      <div id="selectdiv" style={{ textAlign: "center" }}>

        <div>
          {curLoc && <div id='variDD' >

            {varOpt && <select onChange={_onVarSelect}>

              {varOpt.map((opt) => <option key={opt} value={opt}>{opt}</option>)}

            </select>}

          </div>}

          {curLoc && <div className='marginItem' >

            <select onChange={_onLocationSelect} value={curLoc}>
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

        {arc}


      </div>

    </div>
  );

}


export default Unison;

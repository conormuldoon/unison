
import React, { useState, useEffect } from 'react';
import 'react-day-picker/lib/style.css';
import { formatDate } from 'react-day-picker/moment';
import './App.css';
import ARLocationComponent from './ARLocationComponent';
import { FORMAT, SELF } from './Constant';
import DateSelector from './DateSelector';
import { today, tomorrow, problemConnecting, varMapping } from './Util';
import HttpStatus from 'http-status-codes';
import PropTypes from 'prop-types';
import { MapFactory, MapMarkerFactory } from './LeafletMap';
import { Marker, Tooltip } from 'react-leaflet';

import { createLocationFactory } from './LocationForm';

import { createRemoveFactory } from './RemoveComponent';
import { createPopupFactory } from './ChartPopup';
import parser from 'uri-template';


export interface UnisonProps {

  /**
   * A function for creating the map. 
   */
  createMap: MapFactory;
  children?: React.ReactNode;

}


type ModelLink = {
  /**
   * The self URI for the HAL location collection.
   */
  "self": string,

  /**
   * The contains URI for the HAL location collection.
   */
  "contains": string

}


/**
 * Thrown when there is a bad response from the server in obtaining the
 * HAL models or the GeoJSON location collection. 
 */
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

/**
 * Throws an error for the endpoint if the was response was not ok.
 * 
 * @param ok True if the response was ok and false otherwise.
 * @param endpoint The endpoint the resquest was made to.
 * @param status The HTTP status code.
 */
function checkResponse(ok: boolean, endpoint: string, status: number) {
  if (!ok)
    throw new ResponseError('Bad response for ' + endpoint, status);
}

const OMIT = 'omit';

/**
 * Creates request options for GeoJSON and HAL requests.
 * 
 * @param accept Used in creating the accept header.
 * @returns Options for the credentials and headers.
 */
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

/**
 * Creates a mapping between HAL rels and URIs.
 * 
 * @param listItem An object that has a number of HAL rels.
 * @returns The map created, which will be associated with a specific location..
 */
function createLinkMap(listItem: Link) {
  const itemMap = new Map<string, string>();
  for (const rel in listItem) {
    itemMap.set(rel, listItem[rel].href);

  }
  return itemMap;
}

/**
 * Replaces spaces with underscores.
 * 
 * @param s The source string.
 * @returns The converted string.
 */
const spaceToUnderscore = (s: string) => {
  return s.replace(/ /g, '_')
}

/**
 * Uses upper case letters to split strings can creating weather variable option names to display to the user
 * in capital case. For example, the string 'WindDirection' would be converted to 'Wind Direction' 
 * 
 * @param s The source string.
 * @returns The converted string.
 */
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

/**
 * Converts the HAL relation keys for links received to weather variable options to display to the user.
 * 
 * @param vo An array that the weather variable options are pushed to.
 * @param link The HAL links.
 */
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

const locationMap = new Map<string, LinkMap>();
const varOpt: string[] = [];
const option: string[] = [];


/**
 * Application component for Unison. Once mounted, it connects to the back-end to receive a list of the locations being tracked.
 * 
 * @component
 * 
 */
const Unison: React.FC<UnisonProps> = ({ createMap, children }) => {


  const [fromDate, setFromDate] = useState(today());
  const [toDate, setToDate] = useState(tomorrow());
  const [marker, setMarker] = useState<MapMarkerFactory[]>([]);
  const [curLoc, setCurLoc] = useState<string | null>(null);
  const [curVar, setCurVar] = useState<string | null>(null);
  const [modelLink, setModelLink] = useState<ModelLink | null>(null);

  /**
   * Requests data in a GeoJSON Feature Collection format and
   * adds markers for locations to the Leaflet map.
   * 
   * @param uri The URI where the data is obtained from.
   */
  async function requestFeatureCollection(uri: string) {
    const response = await fetch(uri, jsonHeader('geo'));


    checkResponse(response.ok, uri, response.status);

    const fc = await response.json();

    const locationArray = fc.features;
    const n = locationArray.length;


    const newMarker: MapMarkerFactory[] = [];
    option.length = 0;
    for (let i = 0; i < n; i++) {
      option.push(locationArray[i].properties.name);
      const pos: [number, number] = [locationArray[i].geometry.coordinates[1], locationArray[i].geometry.coordinates[0]];

      const properties = locationArray[i].properties;
      newMarker.push(function (component, callback, image): JSX.Element {
        return <Marker key={properties.name} position={pos} onClick={callback.bind(component, properties.name)} icon={image} >
          <Tooltip>{properties.name}</Tooltip>
        </Marker>
      });

    }

    if (n > 0) {

      setMarker(newMarker);


    } else {

      setMarker([]);
    }


  }

  function clearCurrent() {

    setCurLoc(null);
    setCurVar(null);
    varOpt.length = 0;
    option.length = 0;
    locationMap.clear();

  }

  function processModelList(list: [{ name: string, _links: Link }]) {

    const n = list.length;

    locationMap.clear();

    for (let i = 0; i < n; i++) {

      locationMap.set(list[i].name, createLinkMap(list[i]._links));
    }

    varOpt.length = 0;

    if (n > 0) {
      variableOptions(varOpt, list[0]._links);

    }

    if (varOpt.length > 0) {
      setCurLoc(list[0].name);
      setCurVar(varOpt[0]);
    } else {

      clearCurrent();
      alert("No weather data is associated with the collection.")
    }
  }

  /**
   * Obtains a HAL model of the collection of locations stored on the server and stores links associated with locations.
   * 
   * @param uri The URI to obtain the model from.
   */
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

    if (curLoc && curVar) {
      const linkMap = locationMap.get(curLoc);
      if (linkMap) {
        const href = linkMap.get(varMapping(curVar));
        if (href) {
          const template = parser.parse(href);
          return template.expand({ name: curLoc, fromDate: fromDate, toDate: toDate });
        }
      }
    }


  }

  const handleCSV = async () => {

    const uri = expandLink();
    if (uri) {
      const response = await fetch(uri, {
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
  }


  function createSelector(label: string, dateValue: string, handleDayChange: (day: Date) => void) {
    return <DateSelector label={label} dateValue={dateValue} handleDayChange={handleDayChange} />;
  }

  let guiMap;
  if (curLoc && curVar) {
    const uri = expandLink();
    if (uri) {
      const popupFactory = createPopupFactory(uri, curVar, curLoc);
      guiMap = createMap(marker, markerClicked, popupFactory)
    }
  } else {
    guiMap = createMap(marker, markerClicked);
  }


  function buildRemoveFactory() {
    if (curLoc) {

      const linkMap = locationMap.get(curLoc);
      if (linkMap) {
        const href = linkMap.get(SELF);
        if (href)
          return createRemoveFactory(obtainData, curLoc, href);
      }
    }
  }


  let arc;
  if (modelLink) {
    const createRemove = buildRemoveFactory();
    arc = <ARLocationComponent createLocation={createLocationFactory(obtainData, modelLink.self, modelLink.contains)}
      createRemove={createRemove} />
  }

  return (
    <div id="mapdiv">

      <div id="logos" style={{ textAlign: "center" }} >

        {children}


      </div>

      {guiMap}

      <div id="selectdiv" style={{ textAlign: "center" }}>

        <div>
          {curLoc && <div id='variDD' >

            <select onChange={_onVarSelect}>

              {varOpt.map((opt) => <option key={opt} value={opt}>{opt}</option>)}

            </select>

          </div>}

          {curLoc && <div className='marginItem' >

            <select onChange={_onLocationSelect} value={curLoc}>

              {option.map((opt) => <option key={opt} value={opt}>{opt}</option>)}

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


Unison.propTypes = {


  createMap: PropTypes.func.isRequired,


}


export default Unison;

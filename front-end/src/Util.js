
import { FORMAT } from './Constant';

import {
  formatDate
} from 'react-day-picker/moment';

import GeoJSON from 'geojson';

export const tomorrow = () => {

  const now = Date.now();
  const today = new Date(now);
  const tomorrow = new Date(now);
  tomorrow.setDate(today.getDate() + 1)

  return formatDate(tomorrow, FORMAT);
}

export const today = () => {
  return formatDate(new Date(Date.now()), FORMAT);
};

export const varMapping = (varCur) => {
  let variRequest = varCur.replace(/ /g, '');
  variRequest = variRequest.charAt(0).toLowerCase() + variRequest.slice(1);
  return variRequest;
}

export const problemConnecting = () => {
  alert('Problem Connecting');
}


const postObject = (body) => {
  return {
    method: 'POST',
    headers: new Headers({
      'Content-Type': 'application/geo+json'
    }),
    body: body
  }
}


export const locationPostObject = (locationName, longitude, latitude) => {
  const location = { name: locationName, lng: longitude, lat: latitude };

  const geoJSONPoint = GeoJSON.parse(location, { Point: ['lat', 'lng'] });
  return postObject(JSON.stringify(geoJSONPoint));
}

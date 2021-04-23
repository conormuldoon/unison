
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
  alert('There was a problem connecting to Unison. Please try again later.');
}


// Changing second word in current variable to lower case if present
export const chartText = (curVar) => {

  let vca = curVar.split(' ');

  let vc = vca[0];

  if (vca.length > 1) {
    vca[1] = vca[1].toLowerCase();

    vc += ' ' + vca[1];
  }

  return vc;
}

const putObject = (body) => {
  return {
    method: 'PUT',
    headers: new Headers({
      'Content-Type': 'application/geo+json'
    }),
    body: body
  }

}


export const locationPutObject = (locationName, longitude, latitude) => {
  const location = { name: locationName, lng: longitude, lat: latitude };

  const geoJSONPoint = GeoJSON.parse(location, { Point: ['lat', 'lng'] });
  return putObject(JSON.stringify(geoJSONPoint));
}

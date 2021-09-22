
import { FORMAT } from './Constant';

import {
  formatDate
} from 'react-day-picker/moment';

import GeoJSON from 'geojson';

export const tomorrow = (): Date => {

  const now = Date.now();
  const today = new Date(now);
  const tomorrow = new Date(now);
  tomorrow.setDate(today.getDate() + 1)

  return formatDate(tomorrow, FORMAT);
}

export const today = (): Date => {
  return formatDate(new Date(Date.now()), FORMAT);
};

export const varMapping = (varCur: string): string => {
  let variRequest = varCur.replace(/ /g, '');
  variRequest = variRequest.charAt(0).toLowerCase() + variRequest.slice(1);
  return variRequest;
}

export const problemConnecting = (): void => {
  alert('There was a problem connecting to Unison. Please try again later.');
}


// Changing second word in current variable to lower case if present
export const chartText = (curVar: string): string => {

  const vca = curVar.split(' ');

  let vc = vca[0];

  if (vca.length > 1) {
    vca[1] = vca[1].toLowerCase();

    vc += ' ' + vca[1];
  }

  return vc;
}

export function csrfToken(): string {

  // Modified regular expression from an anwswer to https://stackoverflow.com/questions/51109559/get-cookie-with-react
  // and removed unnecessary slash.
  return document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*=\s*([^;]*).*$)|^.*$/, "$1");

}



const putObject = (body: string) => {
  return {
    method: 'PUT',
    headers: new Headers({
      'Content-Type': 'application/geo+json',
      'X-XSRF-TOKEN': csrfToken()
    }),
    body: body
  }

}


export const locationPutObject = (locationName: string, longitude: string, latitude: string):
  Record<string, unknown> => {

  const location = { name: locationName, lng: longitude, lat: latitude };

  const geoJSONPoint = GeoJSON.parse(location, { Point: ['lat', 'lng'] });
  return putObject(JSON.stringify(geoJSONPoint));
}

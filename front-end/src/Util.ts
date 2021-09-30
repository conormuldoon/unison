
import { FORMAT } from './Constant';

import {
  formatDate
} from 'react-day-picker/moment';

import GeoJSON from 'geojson';

/**
 * Cretes a string for tomorrow's date.
 * 
 * @returns Tomorrow's date formatted in terms of day of the month, month, and year.
 */
export const tomorrow = (): string => {

  const now = Date.now();
  const today = new Date(now);
  const tomorrow = new Date(now);
  tomorrow.setDate(today.getDate() + 1)

  return formatDate(tomorrow, FORMAT);
}

/**
 * Cretes a string for tomorros's date.
 * 
 * @returns Tomorrow's date formatted in terms of day of the month, month, and year.
 */
export const today = (): string => {
  return formatDate(new Date(Date.now()), FORMAT);
};

/**
 * Converts a weather variable that may be selected by the user to a mapping suffix for an endpoint
 * on the server. The initial character of the weather variable is changed to lower case and words 
 * are concatenated. For example, 'Wind Direction' is converted to 'windDirection'. 
 * 
 * @param varCur The weather variable.
 * @returns The converted string.
 */
export const varMapping = (varCur: string): string => {
  let variRequest = varCur.replace(/ /g, '');
  variRequest = variRequest.charAt(0).toLowerCase() + variRequest.slice(1);
  return variRequest;
}

/**
 * Displays a message to te user that there was a problem connecting to the server.
 */
export const problemConnecting = (): void => {
  alert('There was a problem connecting to Unison. Please try again later.');
}


/**
 * Changes second word in a weather variable to lower case if present so that the second
 * word is not capitalised in a sentence. For example, the weather variable 'Wind Direction' 
 * would be changed to 'Wind direction'.
 * 
 * @param curVar The weather variable to be changed.
 * @returns The converted string.
 */
export const chartText = (curVar: string): string => {

  const vca = curVar.split(' ');

  let vc = vca[0];

  if (vca.length > 1) {
    vca[1] = vca[1].toLowerCase();

    vc += ' ' + vca[1];
  }

  return vc;
}

/**
 * Obtains the CSRF token with the key XSRF-TOKEN from the stored cookie.
 * 
 * @returns The CSRF toke.
 */
export function csrfToken(): string {

  // Modified regular expression from an anwswer to https://stackoverflow.com/questions/51109559/get-cookie-with-react
  // and removed unnecessary slash.
  return document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*=\s*([^;]*).*$)|^.*$/, "$1");

}


/**
 * Creates request options for making put requests to the server for JSON. 
 * 
 * @param body The JSON body of the request.
 * @returns The options created. The optinos include the a X-XSRF-TOKEN header for the CSRF token.
 */
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

/**
 * Creates put options with a GeoJSON representation of the location in the body.
 * 
 * @param locationName The name of the location to be put to the server.
 * @param longitude The longitute coordinate of the location. 
 * @param latitude The latitude coordinate of the location. 
 * 
 * @returns The put options create, which include a X-XSRF-TOKEN header for the CSRF token.
 */
export const locationPutObject = (locationName: string, longitude: string, latitude: string):
  Record<string, unknown> => {

  const location = { name: locationName, lng: longitude, lat: latitude };

  const geoJSONPoint = GeoJSON.parse(location, { Point: ['lat', 'lng'] });
  return putObject(JSON.stringify(geoJSONPoint));
}

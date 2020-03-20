
import { FORMAT } from './Constant';

import {
  formatDate
} from 'react-day-picker/moment';

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

const postObject = (body) => {
  return {
    method: 'POST',
    headers: new Headers({
      'Content-Type': 'application/x-www-form-urlencoded',
    }),
    body: body
  }
}

export const locCred = (location, uname, pword) => {
  return 'location=' + location + '&username=' + uname + '&password=' + pword;
}

export const removePostObject = (location, uname, pword) => {
  return postObject(locCred(location, uname, pword));

}

export const locationPostObject = (location, uname, pword, lon, lat) => {
  return postObject(locCred(location, uname, pword) + '&longitude=' + lon + '&latitude=' + lat);
}

export const varMapping = (varCur) => {
  let variRequest = varCur.replace(/ /g, '');
  variRequest = variRequest.charAt(0).toLowerCase() + variRequest.slice(1);
  return variRequest;
}

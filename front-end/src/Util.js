
import {FORMAT} from './Constant';

import  {
  formatDate
} from 'react-day-picker/moment';

export const fromDate = () =>{

  const today=new Date(Date.now());
  const fDate=new Date(Date.now());
  fDate.setMonth(today.getMonth()-1);

  return formatDate(fDate,FORMAT);
}

export const today = () => {
  return formatDate(new Date(Date.now()), FORMAT);
};

const postObject = (body) =>{
  return {
    method: 'POST',
    headers: new Headers({
               'Content-Type': 'application/x-www-form-urlencoded',
      }),
    body: body
  }
}

const locCred = (location,uname,pword) =>{
  return 'location='+location+'&username='+uname+'&password='+pword;
}

export const removePostObject = (location,uname,pword) =>{
  return postObject(locCred(location,uname,pword));

}

export const locationPostObject = (location,uname,pword,lon,lat) =>{
  return postObject(locCred(location,uname,pword)+'&longitude='+lon+'&latitude='+lat);
}

export const varMapping = (varCur) =>{
  let variRequest=varCur.replace(/ /g,'');
  variRequest=variRequest.charAt(0).toLowerCase() + variRequest.slice(1);
  return variRequest;
}

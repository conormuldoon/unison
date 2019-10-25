
import {FORMAT} from './Constant';

import  {
  formatDate
} from 'react-day-picker/moment';

export const fromDate = () =>{

  const today=new Date(Date.now());
  let lMonth=today.getMonth()-1;
  const year=today.getFullYear();
  const fDate=new Date();

  if(lMonth<0){
    lMonth=11;
    fDate.setYear(year-1);
  }

  fDate.setDate(1);
  fDate.setMonth(lMonth);
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

import React from 'react';
import ReactDOM from 'react-dom';
import TabsComponent from './TabsComponent';
import fetchMock from 'fetch-mock';
import {FORMAT} from './Constant';
import { act } from 'react-dom/test-utils';
import "@testing-library/jest-dom/extend-expect";
import {varMapping} from './Util';
import  {
  formatDate
} from 'react-day-picker/moment';

import { render, waitForElement } from "react-testing-library"


const fromDate=formatDate(new Date(2018,11,20),FORMAT);
const toDate=formatDate(new Date(2019,3,4),FORMAT);
const tabsComponent=<TabsComponent varCur='Precipitation' location='UCD' fromDate={fromDate} toDate={toDate}/>;

const apiRequest='end:/precipitation?location=UCD&fromDate=20/12/2018&toDate=4/4/2019';
it('renders without crashing', async () => {


  fetchMock.get(apiRequest, []);

  const div = document.createElement('div');

  ReactDOM.render(tabsComponent,div);
  ReactDOM.unmountComponentAtNode(div);

  fetchMock.restore();
});

it('mathes snapshot', () => {

  fetchMock.get(apiRequest, []);

  const {container} = render(tabsComponent);

  expect(container).toMatchSnapshot();

  fetchMock.restore();

});

it("doesn't crash when no data obtained from server", async ()=>{
  const date='2/4/2019';
  const location='UCD';
  fetchMock.get('end:/humidity?location='+location+'&fromDate='+date+'&toDate='+date,[]);
  const {getByTestId} = render(<TabsComponent varCur='Humidity' location={location} fromDate={date} toDate={date} />);

  const chartDiv=await waitForElement(()=>getByTestId('chart'));
  fetchMock.restore();

});

const addChart = async (weatherVariable,yLabel,data) =>{
  const date='2/4/2019';
  const location='UCD';

  fetchMock.get('end:/'+varMapping(weatherVariable)+'?location='+location+'&fromDate='+date+'&toDate='+date,
      data);

  const {getByText} = render(<TabsComponent varCur={weatherVariable} location={location} fromDate={date} toDate={date}  />);

  await waitForElement(()=>getByText(yLabel));

  fetchMock.restore();

}

it('adds a chart for temperature', async () =>{
  await addChart('Temperature','Celsius',[
        {
           "date": "2019-04-01T23:00:00.000+0000",
           "temperature": 5.5
       },
       {
           "date": "2019-04-02T00:00:00.000+0000",
           "temperature": 4.8
       },
       {
           "date": "2019-04-02T01:00:00.000+0000",
           "temperature": 4.5
       },
       {
           "date": "2019-04-02T02:00:00.000+0000",
           "temperature": 3.9
       },
       {
           "date": "2019-04-02T03:00:00.000+0000",
           "temperature": 3.7
       },
       {
           "date": "2019-04-02T04:00:00.000+0000",
           "temperature": 3.4
       },
       {
           "date": "2019-04-02T05:00:00.000+0000",
           "temperature": 3.2
       },
       {
           "date": "2019-04-02T06:00:00.000+0000",
           "temperature": 3.4
       },
       {
           "date": "2019-04-02T07:00:00.000+0000",
           "temperature": 3.9
       },
       {
           "date": "2019-04-02T08:00:00.000+0000",
           "temperature": 4.9
       },
       {
           "date": "2019-04-02T09:00:00.000+0000",
           "temperature": 6.1
       },
       {
           "date": "2019-04-02T10:00:00.000+0000",
           "temperature": 6.1
       },
       {
           "date": "2019-04-02T11:00:00.000+0000",
           "temperature": 7.5
       },
       {
           "date": "2019-04-02T12:00:00.000+0000",
           "temperature": 8.9
       }
  ]);
});


it('adds tabs for precipitation', async () =>{

  await addChart('Precipitation','Millimetres', [{
        "date": "2019-04-01T23:00:00.000+0000",
        "precipitation": {
            "value": 0,
            "minvalue": 0,
            "maxvalue": 0.1
        }
    },
    {
        "date": "2019-04-02T00:00:00.000+0000",
        "precipitation": {
            "value": 0,
            "minvalue": 0,
            "maxvalue": 0
        }
    },
    {
        "date": "2019-04-02T01:00:00.000+0000",
        "precipitation": {
            "value": 0,
            "minvalue": 0,
            "maxvalue": 0
        }
    },
    {
        "date": "2019-04-02T02:00:00.000+0000",
        "precipitation": {
            "value": 0,
            "minvalue": 0,
            "maxvalue": 0
        }
    },
    {
        "date": "2019-04-02T03:00:00.000+0000",
        "precipitation": {
            "value": 0,
            "minvalue": 0,
            "maxvalue": 0
        }
    },
    {
        "date": "2019-04-02T04:00:00.000+0000",
        "precipitation": {
            "value": 0,
            "minvalue": 0,
            "maxvalue": 0
        }
    },
    {
        "date": "2019-04-02T05:00:00.000+0000",
        "precipitation": {
            "value": 0,
            "minvalue": 0,
            "maxvalue": 0
        }
    },
    {
        "date": "2019-04-02T06:00:00.000+0000",
        "precipitation": {
            "value": 0,
            "minvalue": 0,
            "maxvalue": 0
        }
    },
    {
        "date": "2019-04-02T07:00:00.000+0000",
        "precipitation": {
            "value": 0,
            "minvalue": 0,
            "maxvalue": 0
        }
    },
    {
        "date": "2019-04-02T08:00:00.000+0000",
        "precipitation": {
            "value": 0,
            "minvalue": 0,
            "maxvalue": 0
        }
    },
    {
        "date": "2019-04-02T09:00:00.000+0000",
        "precipitation": {
            "value": 0,
            "minvalue": 0,
            "maxvalue": 0
        }
    },
    {
        "date": "2019-04-02T10:00:00.000+0000",
        "precipitation": {
            "value": 0,
            "minvalue": 0,
            "maxvalue": 0
        }
    }]);



});

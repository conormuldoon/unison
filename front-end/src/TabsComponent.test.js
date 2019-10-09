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

import { render, waitForElement, wait} from "react-testing-library"
import expectExport from 'expect';
const DLEN=16;




const apiRequest='end:/precipitation?location=UCD&fromDate=20/12/2018&toDate=4/4/2019';
/*it('renders without crashing', async () => {


  fetchMock.get(apiRequest, []);

  const div = document.createElement('div');

  ReactDOM.render(<TabsComponent />,div);
  ReactDOM.unmountComponentAtNode(div);

  fetchMock.restore();
});

it('mathes snapshot', () => {

  fetchMock.get(apiRequest, []);

  const {container} = render(<TabsComponent />);

  expect(container).toMatchSnapshot();

  fetchMock.restore();

});*/
/*
it("doesn't crash when no data obtained from server", async ()=>{
  const date='2/4/2019';
  const location='UCD';
  fetchMock.get('end:/humidity?location='+location+'&fromDate='+date+'&toDate='+date,[]);
  const {findByTestId} = render(<TabsComponent varCur='Humidity' location={location} fromDate={date} toDate={date} />);

  findByTestId('chart');
  fetchMock.restore();

});
*/

const addChart = async (weatherVariable,dataArray,zoomDomain) =>{
  
    const n=dataArray.length;

    for(let i=0;i<n;i++){
      const dateS=dataArray[i].date;
      dataArray[i].date=new Date(dateS.substring(0,DLEN));

    }
 
  
  const {findByTestId,debug} = render(<TabsComponent varCur={weatherVariable} data={dataArray} zoomDomain={zoomDomain} minMax={false} />);
 
  //console.log(weatherVariable);
  
  const chartDiv=findByTestId('chart');
 
  //expect(chartDiv).toBeDefined();
  
  fetchMock.restore();

}




/* 
it('adds a chart for humidity', async () =>{
    await addChart('Humidity',[
        {
            "date": "2019-10-07T23:00:00.000+0000",
            "humidity": 74.2
        },
        {
            "date": "2019-10-08T00:00:00.000+0000",
            "humidity": 77.4
        },
        {
            "date": "2019-10-08T01:00:00.000+0000",
            "humidity": 75.3
        }
    ],{ x: ['2019-10-07T23:00:00.000+0000', '2019-10-08T01:00:00.000+0000'] }

    );
});




it('adds tabs for the low, medium, and high cloud level values', async () =>{
    await addChart('Cloud Level',[
     
        {
            "date": "2019-10-08T12:00:00.000+0000",
            "cloud": {
                "low": 75.9,
                "medium": 5,
                "high": 0
            }
        },
        {
            "date": "2019-10-08T13:00:00.000+0000",
            "cloud": {
                "low": 65.6,
                "medium": 0,
                "high": 0.2
            }
        },
        {
            "date": "2019-10-08T14:00:00.000+0000",
            "cloud": {
                "low": 68.5,
                "medium": 3.8,
                "high": 71.8
            }
        },
        {
            "date": "2019-10-08T15:00:00.000+0000",
            "cloud": {
                "low": 54.6,
                "medium": 1.8,
                "high": 0
            }
        },
        {
            "date": "2019-10-08T16:00:00.000+0000",
            "cloud": {
                "low": 57.8,
                "medium": 54.2,
                "high": 0
            }
        },
        {
            "date": "2019-10-08T17:00:00.000+0000",
            "cloud": {
                "low": 58.9,
                "medium": 0.3,
                "high": 0
            }
        },
        {
            "date": "2019-10-08T18:00:00.000+0000",
            "cloud": {
                "low": 39.3,
                "medium": 0,
                "high": 0
            }
        },
        {
            "date": "2019-10-08T19:00:00.000+0000",
            "cloud": {
                "low": 43.2,
                "medium": 0,
                "high": 0
            }
        },
        {
            "date": "2019-10-08T20:00:00.000+0000",
            "cloud": {
                "low": 67.1,
                "medium": 0,
                "high": 0
            }
        },
        {
            "date": "2019-10-08T21:00:00.000+0000",
            "cloud": {
                "low": 32.7,
                "medium": 0.1,
                "high": 7
            }
        },
        {
            "date": "2019-10-08T22:00:00.000+0000",
            "cloud": {
                "low": 16.3,
                "medium": 1.8,
                "high": 50
            }
        }
    ]);
    }

);



it('adds a chart for temperature', async () =>{
  await addChart('Temperature',[
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
*/

it('adds tabs for precipitation', async () =>{

  await addChart('Precipitation', [{
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
    }],{ x: [new Date('2019-04-01T23:00:00.000+0000'.substring(0,DLEN)), new Date('2019-04-02T10:00:00.000+0000'.substring(0,DLEN))] });



});

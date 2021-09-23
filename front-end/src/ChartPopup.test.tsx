import { enableFetchMocks } from 'jest-fetch-mock';
enableFetchMocks();

import fetchMock from 'jest-fetch-mock';
import React from 'react';
import { render, screen } from "@testing-library/react";
import ChartPopup from './ChartPopup';
import { PRECIP, CL } from './Constant';
import { chartText } from './Util';

const fromDate = '1-2-2018';
const toDate = '7-10-2019';

const location = {
  "name": "UCD",
  "_links": {
    "self": {
      "href": "http://localhost:8080/locationCollection/UCD"
    },
    "cloudiness": {
      "href": "http://localhost:8080/locationCollection/UCD/cloudiness{?fromDate,toDate}",
      "templated": true
    },
    "cloudLevel": {
      "href": "http://localhost:8080/locationCollection/UCD/cloudLevel{?fromDate,toDate}",
      "templated": true
    },
    "dewPoint": {
      "href": "http://localhost:8080/locationCollection/UCD/dewPoint{?fromDate,toDate}",
      "templated": true
    },
    "humidity": {
      "href": "http://localhost:8080/locationCollection/UCD/humidity{?fromDate,toDate}",
      "templated": true
    },
    "precipitation": {
      "href": "http://localhost:8080/locationCollection/UCD/precipitation{?fromDate,toDate}",
      "templated": true
    },
    "pressure": {
      "href": "http://localhost:8080/locationCollection/UCD/pressure{?fromDate,toDate}",
      "templated": true
    },
    "temperature": {
      "href": "http://localhost:8080/locationCollection/UCD/temperature{?fromDate,toDate}",
      "templated": true
    },
    "windDirection": {
      "href": "http://localhost:8080/locationCollection/UCD/windDirection{?fromDate,toDate}",
      "templated": true
    },
    "windSpeed": {
      "href": "http://localhost:8080/locationCollection/UCD/windSpeed{?fromDate,toDate}",
      "templated": true
    }
  }
}


const uri = "http://localhost:8080/locationCollection/UCD/precipitation?fromDate=" + fromDate + "&toDate=" + toDate;

const chartPopup = <ChartPopup curVar='Precipitation'
  closePopup={() => { }} uri={uri} name={location.name} />;

const apiRequest = 'end:?fromDate=1-2-2018&toDate=7-10-2019';


const data = [{ "date": "2019-02-23T19:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-23T20:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-23T21:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-23T22:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-23T23:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T18:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T12:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T13:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T14:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T15:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T16:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T17:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T18:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T19:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T20:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T21:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T22:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T23:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T00:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T01:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T02:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T03:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T04:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T05:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T06:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T07:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T08:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T09:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T10:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T11:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T12:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T13:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T14:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T15:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T16:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T17:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T00:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T01:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T02:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T03:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T04:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T05:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T06:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T07:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T08:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.1 } }, { "date": "2019-02-24T09:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.1 } }, { "date": "2019-02-24T10:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T11:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T12:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T13:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T14:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T15:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T16:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T17:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T18:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T19:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T20:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T21:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T22:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T23:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T00:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T01:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T02:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T03:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T04:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T05:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T06:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T07:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T08:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T09:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T10:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T11:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T00:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T01:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T02:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T03:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T04:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T05:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T06:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T07:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T08:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T09:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T10:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T11:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T12:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T13:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T14:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T15:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T16:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T17:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T18:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T19:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T20:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T21:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T22:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T23:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-28T00:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-28T01:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-28T02:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-28T03:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-28T04:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-28T05:00:00.000+0000", "precipitation": { "value": 0.2, "minvalue": 0.1, "maxvalue": 0.3 } }, { "date": "2019-02-26T19:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T20:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T21:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T22:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T23:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }];

it('renders without crashing', async () => {


  fetchMock.mockOnce("[]");

  render(chartPopup);
  await screen.findByRole('button', { name: 'email' });

});



it('mathes snapshot', async () => {

  fetchMock.mockResponse(JSON.stringify(data));

  const { container } = render(chartPopup);
  await screen.findByText('Precipitation data from ' + location.name);
  expect(container).toMatchSnapshot();

  fetchMock.resetMocks();

});


it('displays text for the selected variable and location', async () => {


  const VAR_OPT = [PRECIP, 'Humidity', 'Wind Direction', 'Wind Speed', 'Cloudiness', CL, 'Dew Point', 'Pressure', 'Temperature'];
  for (const vo of VAR_OPT) {
    let sOpt = chartText(vo);
    for (const loc of [{
      "name": "Dublin",
      "_links": {
        "self": {
          "href": "http://localhost:8080/locationCollection/Dublin"
        },
        "cloudiness": {
          "href": "http://localhost:8080/locationCollection/Dublin/cloudiness{?fromDate,toDate}",
          "templated": true
        },
        "cloudLevel": {
          "href": "http://localhost:8080/locationCollection/Dublin/cloudLevel{?fromDate,toDate}",
          "templated": true
        },
        "dewPoint": {
          "href": "http://localhost:8080/locationCollection/Dublin/dewPoint{?fromDate,toDate}",
          "templated": true
        },
        "humidity": {
          "href": "http://localhost:8080/locationCollection/Dublin/humidity{?fromDate,toDate}",
          "templated": true
        },
        "precipitation": {
          "href": "http://localhost:8080/locationCollection/Dublin/precipitation{?fromDate,toDate}",
          "templated": true
        },
        "pressure": {
          "href": "http://localhost:8080/locationCollection/Dublin/pressure{?fromDate,toDate}",
          "templated": true
        },
        "temperature": {
          "href": "http://localhost:8080/locationCollection/Dublin/temperature{?fromDate,toDate}",
          "templated": true
        },
        "windDirection": {
          "href": "http://localhost:8080/locationCollection/Dublin/windDirection{?fromDate,toDate}",
          "templated": true
        },
        "windSpeed": {
          "href": "http://localhost:8080/locationCollection/Dublin/windSpeed{?fromDate,toDate}",
          "templated": true
        }
      }
    }, {
      "name": "London",
      "_links": {
        "self": {
          "href": "http://localhost:8080/locationCollection/London"
        },
        "cloudiness": {
          "href": "http://localhost:8080/locationCollection/London/cloudiness{?fromDate,toDate}",
          "templated": true
        },
        "cloudLevel": {
          "href": "http://localhost:8080/locationCollection/London/cloudLevel{?fromDate,toDate}",
          "templated": true
        },
        "dewPoint": {
          "href": "http://localhost:8080/locationCollection/London/dewPoint{?fromDate,toDate}",
          "templated": true
        },
        "humidity": {
          "href": "http://localhost:8080/locationCollection/London/humidity{?fromDate,toDate}",
          "templated": true
        },
        "precipitation": {
          "href": "http://localhost:8080/locationCollection/London/precipitation{?fromDate,toDate}",
          "templated": true
        },
        "pressure": {
          "href": "http://localhost:8080/locationCollection/London/pressure{?fromDate,toDate}",
          "templated": true
        },
        "temperature": {
          "href": "http://localhost:8080/locationCollection/London/temperature{?fromDate,toDate}",
          "templated": true
        },
        "windDirection": {
          "href": "http://localhost:8080/locationCollection/London/windDirection{?fromDate,toDate}",
          "templated": true
        },
        "windSpeed": {
          "href": "http://localhost:8080/locationCollection/London/windSpeed{?fromDate,toDate}",
          "templated": true
        }
      }
    }]) {

      fetchMock.mockOnce(JSON.stringify(loc));

      const uri = "http://localhost:8080/locationCollection/London/windSpeed?fromDate=" + fromDate + "&toDate=" + toDate;
      render(<ChartPopup curVar={vo} name={loc.name} uri={uri} closePopup={() => { }} />);

      await screen.findByText(sOpt + ' data from ' + loc.name);
    }
  }

  fetchMock.resetMocks();
});



it('displays a lower case letter for second word', async () => {

  fetchMock.mockOnce("[]");

  const wdURI = "http://localhost:8080/locationCollection/London/windDirection?fromDate=" + fromDate + "&toDate=" + toDate;
  render(<ChartPopup curVar={'Wind Direction'} name={location.name} uri={wdURI}
    closePopup={() => { }} />);

  await screen.findByText('Wind direction data from ' + location.name);



});

it('adds a chart', async () => {

  fetchMock.mockOnce(JSON.stringify(data));

  render(chartPopup);
  await screen.findByTestId('chart');

});


import fetchMock from 'fetch-mock';
import React from 'react';
import ReactDOM from 'react-dom';
import { render,  waitFor, screen } from "@testing-library/react";
import ChartPopup from './ChartPopup';
import { PRECIP, CL } from './Constant';
import { chartText } from './Util';
import { expandLink } from './Util';

const fromDate = '1/2/2018';
const toDate = '7/10/2019';

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

const uri = expandLink(location, 'Precipitation', fromDate, toDate);

const chartPopup = <ChartPopup curVar='Precipitation'
  closePopup={() => { }} uri={uri} name={location.name} />;

const apiRequest = 'end:?fromDate=1%2F2%2F2018&toDate=7%2F10%2F2019';


const data = [{ "date": "2019-02-23T19:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-23T20:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-23T21:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-23T22:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-23T23:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T18:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T12:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T13:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T14:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T15:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T16:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T17:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T18:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T19:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T20:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T21:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T22:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T23:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T00:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T01:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T02:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T03:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T04:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T05:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T06:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T07:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T08:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T09:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T10:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T11:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T12:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T13:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T14:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T15:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T16:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T17:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T00:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T01:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T02:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T03:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T04:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T05:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T06:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T07:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T08:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.1 } }, { "date": "2019-02-24T09:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.1 } }, { "date": "2019-02-24T10:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T11:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T12:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T13:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T14:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T15:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T16:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T17:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T18:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T19:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T20:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T21:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T22:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-24T23:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T00:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T01:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T02:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T03:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T04:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T05:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T06:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T07:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T08:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T09:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T10:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-25T11:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T00:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T01:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T02:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T03:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T04:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T05:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T06:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T07:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T08:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T09:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T10:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T11:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T12:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T13:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T14:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T15:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T16:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T17:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T18:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T19:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T20:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T21:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T22:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-27T23:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-28T00:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-28T01:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-28T02:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-28T03:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-28T04:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-28T05:00:00.000+0000", "precipitation": { "value": 0.2, "minvalue": 0.1, "maxvalue": 0.3 } }, { "date": "2019-02-26T19:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T20:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T21:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T22:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }, { "date": "2019-02-26T23:00:00.000+0000", "precipitation": { "value": 0.0, "minvalue": 0.0, "maxvalue": 0.0 } }];

it('renders without crashing', async () => {


  fetchMock.get(apiRequest, data);

  const div = document.createElement('div');

  ReactDOM.render(chartPopup, div);
  ReactDOM.unmountComponentAtNode(div);
  fetchMock.restore();
});



it('mathes snapshot', () => {

  fetchMock.get(apiRequest, data);

  const { container } = render(chartPopup);

  expect(container).toMatchSnapshot();

  fetchMock.restore();

});

it('displays text for the selected variable and location', () => {

  fetchMock.get(apiRequest, []);
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

      const uri = expandLink(loc, 'Wind Speed', fromDate, toDate);
      const { getByText } = render(<ChartPopup curVar={vo} name={loc.name} uri={uri} closePopup={() => { }} />);

      getByText(sOpt + ' data from ' + loc.name);
    }
  }

  fetchMock.restore();
});


it('displays a lower case letter for second word', () => {

  fetchMock.get(apiRequest, []);
  const { getByText } = render(<ChartPopup curVar={'Wind Direction'} name={location.name} uri={location._links.windDirection.href}
    closePopup={() => { }} />);

  getByText('Wind direction data from ' + location.name);


  fetchMock.restore();
});

it('adds a chart', () => {

  fetchMock.get(apiRequest, data);

  render(chartPopup);
  waitFor(() => screen.getByTestId('chart'));
  fetchMock.restore();
});


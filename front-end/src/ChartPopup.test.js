import fetchMock from 'fetch-mock';
import React from 'react';
import ReactDOM from 'react-dom';
import { render, waitForElement } from "react-testing-library";
import ChartPopup from './ChartPopup';
import { VAR_OPT } from './Constant';
import { chartText } from './Util';

const fromDate = '1/2/2018';
const toDate = '7/10/2019';

const location = {
  name: "UCD",
  links: {
    cloudiness: "/location/UCD/cloudiness{?fromDate,toDate}",
    cloudLevel: "/location/UCD/cloudLevel{?fromDate,toDate}",
    dewPoint: "/location/UCD/dewPoint{?fromDate,toDate}",
    humidity: "/location/UCD/humidity{?fromDate,toDate}",
    precipitation: "/location/UCD/precipitation{?fromDate,toDate}",
    pressure: "/location/UCD/pressure{?fromDate,toDate}",
    temperature: "/location/UCD/temperature{?fromDate,toDate}",
    windDirection: "/location/UCD/windDirection{?fromDate,toDate}",
    windSpeed: "/location/UCD/windSpeed{?fromDate,toDate}",
    harvest: "/location/UCD/harvest"
  }
};

const chartPopup = <ChartPopup curVar='Precipitation'
  fromDate={fromDate} toDate={toDate} closePopup={() => { }} location={location} />;

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

  for (const vo of VAR_OPT) {
    let sOpt = chartText(vo);
    for (const loc of [{
      name: 'Dublin', links: {
        cloudiness: "/location/Dublin/cloudiness{?fromDate,toDate}",
        cloudLevel: "/location/Dublin/cloudLevel{?fromDate,toDate}",
        dewPoint: "/location/Dublin/dewPoint{?fromDate,toDate}",
        humidity: "/location/Dublin/humidity{?fromDate,toDate}",
        precipitation: "/location/Dublin/precipitation{?fromDate,toDate}",
        pressure: "/location/Dublin/pressure{?fromDate,toDate}",
        temperature: "/location/Dublin/temperature{?fromDate,toDate}",
        windDirection: "/location/Dublin/windDirection{?fromDate,toDate}",
        windSpeed: "/location/Dublin/windSpeed{?fromDate,toDate}",
        harvest: "/location/Dublin/harvest"
      }
    }, {
      name: 'London', links: {
        cloudiness: "/location/London/cloudiness{?fromDate,toDate}",
        cloudLevel: "/location/London/cloudLevel{?fromDate,toDate}",
        dewPoint: "/location/London/dewPoint{?fromDate,toDate}",
        humidity: "/location/London/humidity{?fromDate,toDate}",
        precipitation: "/location/London/precipitation{?fromDate,toDate}",
        pressure: "/location/London/pressure{?fromDate,toDate}",
        temperature: "/location/London/temperature{?fromDate,toDate}",
        windDirection: "/location/London/windDirection{?fromDate,toDate}",
        windSpeed: "/location/London/windSpeed{?fromDate,toDate}",
        harvest: "/location/London/harvest"
      }
    }]) {
      const { getByText } = render(<ChartPopup curVar={vo} location={loc} fromDate={fromDate} toDate={toDate} closePopup={() => { }} />);

      getByText(sOpt + ' data from ' + loc.name);
    }
  }

  fetchMock.restore();
});


it('displays a lower case letter for second word', () => {

  fetchMock.get(apiRequest, []);

  const { getByText } = render(<ChartPopup curVar={'Wind Direction'} location={location} fromDate={fromDate} toDate={toDate}
    closePopup={() => { }} />);

  getByText('Wind direction data from ' + location.name);


  fetchMock.restore();
});

it('adds a chart', () => {

  fetchMock.get(apiRequest, data);

  const { getByTestId } = render(chartPopup);
  waitForElement(() => getByTestId('chart'));
  fetchMock.restore();
});


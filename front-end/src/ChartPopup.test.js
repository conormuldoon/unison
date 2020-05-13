import fetchMock from 'fetch-mock';
import React from 'react';
import ReactDOM from 'react-dom';
import { render, waitForElement } from "react-testing-library";
import ChartPopup from './ChartPopup';
import { VAR_OPT } from './Constant';

const fromDate = '1/2/2018';
const toDate = '7/10/2019';
const chartPopup = <ChartPopup curVar='Precipitation' location='UCD'
  fromDate={fromDate} toDate={toDate} closePopup={() => { }} />;

const apiRequest = 'end:?fromDate=' + fromDate + '&toDate=' + toDate;


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

  fetchMock.get('end:fromDate=' + fromDate + '&toDate=' + toDate, []);

  for (const vo of VAR_OPT) {
    let sOpt = vo.substring(vo.indexOf(' ') + 2);
    for (const location of ['Dublin', 'London']) {
      const { getByText } = render(<ChartPopup curVar={sOpt} location={location} fromDate={fromDate} toDate={toDate} closePopup={() => { }} />);

      getByText(sOpt + ' data from ' + location);
    }
  }

  fetchMock.restore();
});


it('displays a lower case letter for second word', () => {

  fetchMock.get('end:fromDate=' + fromDate + '&toDate=' + toDate, []);

  const { getByText } = render(<ChartPopup curVar={'Wind Direction'} location={'UCD'} fromDate={fromDate} toDate={toDate}
    closePopup={() => { }} />);

  getByText('Wind direction data from UCD');


  fetchMock.restore();
});

it('adds a chart', () => {

  fetchMock.get(apiRequest, data);

  const { getByTestId } = render(chartPopup);
  waitForElement(() => getByTestId('chart'));
  fetchMock.restore();
});


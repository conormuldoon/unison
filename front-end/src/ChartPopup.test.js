import fetchMock from 'fetch-mock';
import React from 'react';
import ReactDOM from 'react-dom';
import { render } from "react-testing-library";
import ChartPopup from './ChartPopup';


const fromDate = '1/2/2018';
const toDate = '7/10/2019';
const chartPopup = <ChartPopup varCur='Temperature' location='UCD'
  fromDate={fromDate} toDate={toDate} closePopup={() => { }} />;

const apiRequest = 'end:/temperature?location=UCD&fromDate=' + fromDate + '&toDate=' + toDate;

it('renders without crashing', async () => {


  fetchMock.get(apiRequest, []);

  const div = document.createElement('div');

  ReactDOM.render(chartPopup, div);
  ReactDOM.unmountComponentAtNode(div);
  fetchMock.restore();
});



it('mathes snapshot', () => {

  fetchMock.get(apiRequest, []);

  const { container } = render(chartPopup);

  expect(container).toMatchSnapshot();

  fetchMock.restore();

});

it('display lower case for second word in variable', () => {

  fetchMock.get('end:/cloudLevel?location=UCD&fromDate=' + fromDate + '&toDate=' + toDate, []);

  const { getByText }=render(<ChartPopup varCur='Cloud Level' location='UCD' fromDate={fromDate} toDate={toDate} closePopup={() => { }} />);
  getByText('Cloud level data from UCD');
  
  fetchMock.restore();
});

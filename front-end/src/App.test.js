import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import fetchMock from 'fetch-mock';
import { render, waitForElement, fireEvent } from "react-testing-library"
import "@testing-library/jest-dom/extend-expect";
import {TODAY,FROM_DATE} from './Util';

import {FORMAT} from './Constant';

import  {
  formatDate
} from 'react-day-picker/moment';



it('renders without crashing', async () => {


  fetchMock.get('end:/location', [{"geom":{"type":"Point","coordinates":[-6.223682,53.308441]},"name":"UCD"}]);

  const div = document.createElement('div');

  ReactDOM.render(<App />,div);
  ReactDOM.unmountComponentAtNode(div);

  fetchMock.restore();
});

it('mathes snapshot', () => {

  fetchMock.get('end:/location', [{"geom":{"type":"Point","coordinates":[-6.223682,53.308441]},"name":"UCD"}]);

  const {container} = render(<App />);

  expect(container).toMatchSnapshot();

  fetchMock.restore();

});

it('displays popup when marker clicked', async () =>{
  fetchMock.get('end:/location', [{"geom":{"type":"Point","coordinates":[-6.223682,53.308441]},"name":"UCD"}]);
  const location='UCD';

  fetchMock.get('end:/precipitation?location=UCD&fromDate='+FROM_DATE+'&toDate='+TODAY, [{
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

  const {getAllByAltText,getByText,getByTestId,debug} = render(<App />);

  const marker = await waitForElement(()=>getAllByAltText('')[1]);
  fireEvent.click(marker);
  await waitForElement(()=>getByText('Median'));

  expect(getByTestId('chart')).toHaveTextContent('Millimetres')
  fetchMock.restore();
});

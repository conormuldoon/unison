import "@testing-library/jest-dom/extend-expect";
import fetchMock from 'fetch-mock';
import React from 'react';
import ReactDOM from 'react-dom';
import { fireEvent, render, waitForElement } from "react-testing-library";
import Unison from './Unison';

const features = { "features": [{ "geometry": { "type": "Point", "coordinates": [-6.223682, 53.308441] }, "properties": { "name": "UCD" } }] };

it('renders without crashing', async () => {


    fetchMock.get('end:/location', features);

    const div = document.createElement('div');

    ReactDOM.render(<Unison mapCentre={[59.922326, 10.751560]} />, div);
    ReactDOM.unmountComponentAtNode(div);

    fetchMock.restore();
});

it('mathes Unison snapshot', () => {


    fetchMock.get('end:/location', features);
    const mockDateNow = jest.fn(() => 1571875200000);
    const dn = global.Date.now;
    global.Date.now = mockDateNow;

    const { container } = render(<Unison mapCentre={[59.922326, 10.751560]} />);

    expect(container).toMatchSnapshot();
    global.Date.now = dn;
    fetchMock.restore();

});

it('displays popup when marker clicked', async () => {
    fetchMock.get('end:/location', features);


    fetchMock.get('end:/precipitation?location=UCD&fromDate=1/9/2019&toDate=23/10/2019', [{
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

    const { getAllByAltText, getByText /*, debug*/ } = render(<Unison mapCentre={[59.922326, 10.751560]} />);

    const marker = await waitForElement(() => getAllByAltText('')[1]);

    fireEvent.click(marker);
    const text = await waitForElement(() => getByText('UCD'));

    expect(text).toBeDefined();
    //debug();
    fetchMock.restore();
});


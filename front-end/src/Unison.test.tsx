
import { enableFetchMocks } from 'jest-fetch-mock';
enableFetchMocks();
import "@testing-library/jest-dom/extend-expect";
import fetchMock from 'jest-fetch-mock';
import React from 'react';
import { fireEvent, render, waitFor, screen } from "@testing-library/react";
import Unison from './Unison';
import { createMapFactory } from './LeafletMap';




const unisonModel = {
    "_links": {
        "self": [
            {
                "href": "http://localhost:8080/"
            },
            {
                "href": "http://localhost:8080/index"
            }
        ],
        "locationCollection": {
            "href": "http://localhost:8080/locationCollection"
        },
        "user": {
            "href": "http://localhost:8080/user"
        }
    }
};

const locationModelHAL = {
    "_embedded": {
        "locationModelList": [
            {
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
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/locationCollection"
        },
        "contains": {
            "href": "http://localhost:8080/locationCollection/contains{?name}",
            "templated": true
        }
    }
};

const locationCollection = {
    "type": "FeatureCollection",
    "features": [
        {
            "type": "Feature",
            "geometry": {
                "type": "Point",
                "coordinates": [
                    -6.4,
                    53.4
                ]
            },
            "properties": {
                "name": "UCD"
            }
        }
    ]
};


const mapCentre: [number, number] = [59.922326, 10.751560];
const mapFactory = createMapFactory(mapCentre);

function mockInitialResponses() {
    fetchMock.mockResponses(
        JSON.stringify(unisonModel),
        JSON.stringify(locationModelHAL),
        JSON.stringify(locationCollection));
}

it('renders without crashing', async () => {

    mockInitialResponses();

    const div = document.createElement('div');
    render(<Unison createMap={mapFactory} />);
    await screen.findByRole('button', { name: 'CSV' });



});

it('mathes Unison snapshot', async () => {


    mockInitialResponses();

    const mockDateNow = jest.fn(() => 1571875200000);
    const dn = global.Date.now;
    global.Date.now = mockDateNow;

    const { container } = render(<Unison createMap={mapFactory} />);
    await screen.findByRole('button', { name: 'CSV' });
    expect(container).toMatchSnapshot();
    global.Date.now = dn;


});

it('displays popup when marker clicked', async () => {




    const precipData = [{
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
    }];




    const mockDateNow = jest.fn(() => 1571875200000);
    const dn = global.Date.now;
    global.Date.now = mockDateNow;

    mockInitialResponses();

    const unison = <Unison createMap={mapFactory} />;
    render(unison);

    fetchMock.mockResponses(
        JSON.stringify(precipData),
        JSON.stringify({ value: false })
    );

    await waitFor(() => screen.getAllByAltText('')[1]);

    fireEvent.click(screen.getAllByAltText('')[1]);


    const text = await waitFor(() => screen.getByText('UCD'));
    expect(text).toBeDefined();

    global.Date.now = dn;

    fetchMock.resetMocks();
});

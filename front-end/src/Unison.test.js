import "@testing-library/jest-dom/extend-expect";
import fetchMock from 'fetch-mock';
import React from 'react';
import ReactDOM from 'react-dom';
import { fireEvent, render, waitForElement } from "react-testing-library";
import Unison from './Unison';



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

it('renders without crashing', async () => {


    fetchMock.get('/', unisonModel);

    const div = document.createElement('div');

    ReactDOM.render(<Unison mapCentre={[59.922326, 10.751560]} />, div);
    ReactDOM.unmountComponentAtNode(div);

    fetchMock.restore();
});

it('mathes Unison snapshot', () => {


    fetchMock.get('/', unisonModel);

    const mockDateNow = jest.fn(() => 1571875200000);
    const dn = global.Date.now;
    global.Date.now = mockDateNow;

    const { container } = render(<Unison mapCentre={[59.922326, 10.751560]} />);

    expect(container).toMatchSnapshot();
    global.Date.now = dn;
    fetchMock.restore();

});

it('displays popup when marker clicked', async () => {


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

    const headers = {
        "Accept": "application/hal+json"
    };
    const options = { headers: headers };

    const headersGEO = {
        "Accept": "application/geo+json"
    };
    const optionsGEO = { headers: headersGEO };

    fetchMock.get('end:/locationCollection', locationModelHAL, options);

    fetchMock.get('end:/locationCollection', locationCollection, { overwriteRoutes: false });


    fetchMock.get('end:/precipitation?fromDate=1/9/2019&toDate=23/10/2019', [{
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



    fetchMock.get('/', unisonModel);

    const unison = <Unison mapCentre={[59.922326, 10.751560]} />;
    const { getAllByAltText, getByText /*, debug*/ } = render(unison);

    const marker = await waitForElement(() => getAllByAltText('')[1]);


    fireEvent.click(marker);

    const text = await waitForElement(() => getByText('UCD'));
    expect(text).toBeDefined();
    //debug();




    fetchMock.restore();
});



import { enableFetchMocks } from 'jest-fetch-mock';
enableFetchMocks();
import "@testing-library/jest-dom/extend-expect";
import fetchMock from 'jest-fetch-mock';
import { fireEvent, render, screen, waitForElementToBeRemoved, act } from "@testing-library/react";
import Unison from './Unison';
import { createMapFactory } from './LeafletMap';
import HttpStatus from 'http-status-codes';
import { PinterestIcon } from 'react-share';


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


describe('Unison', () => {

    beforeEach(() => {
        fetchMock.mockResponses(
            JSON.stringify(unisonModel),
            JSON.stringify(locationModelHAL),
            JSON.stringify(locationCollection));
    });

    it('renders without crashing', async () => {


        render(<Unison createMap={mapFactory} />);
        await screen.findByRole('button', { name: 'CSV' });



    });

    it('matches snapshot', async () => {


        const mockDateNow = jest.fn(() => 1571875200000);
        const dn = global.Date.now;
        global.Date.now = mockDateNow;

        try {
            const { container } = render(<Unison createMap={mapFactory} />);
            await screen.findByRole('button', { name: 'CSV' });
            expect(container).toMatchSnapshot();
        } finally {
            global.Date.now = dn;
        }


    });

    it('displays a button to remove the first location received', async () => {
        render(<Unison createMap={mapFactory} />);
        await screen.findByRole('button', {
            name: 'Remove ' +
                locationCollection.features[0].properties.name
        });
    });


    it('does not display a remove button when no loctions', async () => {
        render(<Unison createMap={mapFactory} />);
        const removeButton = await screen.findByRole('button', {
            name: 'Remove ' +
                locationCollection.features[0].properties.name
        });

        const emptyFC = {
            "type": "FeatureCollection",
            "features": []
        }

        const emptyHALC = {

            "_links": {
                "self": {
                    "href": "http://localhost:8080/locationCollection"
                },
                "contains": {
                    "href": "http://localhost:8080/locationCollection/contains{?name}",
                    "templated": true
                }
            }
        }

        fetchMock.mockResponses(
            ['', { status: HttpStatus.FORBIDDEN }],
            "",
            JSON.stringify(unisonModel),
            JSON.stringify(emptyHALC),
            JSON.stringify(emptyFC),

        );

        const alertSpy = jest.spyOn(window, 'alert');
        alertSpy.mockImplementation(() => { });

        const confirmSpy = jest.spyOn(window, 'confirm');
        confirmSpy.mockImplementation(() => true);

        try {
            fireEvent.click(removeButton);



            await waitForElementToBeRemoved(removeButton);

        } finally {
            confirmSpy.mockClear();
            alertSpy.mockClear();
        }

    });

    it('displays popup when marker clicked', async () => {


        const cloudinessData = [{
            "date": "2022-08-03T23:00:00.000+00:00",
            "cloudiness": 83.1
        },
        {
            "date": "2022-08-04T00:00:00.000+00:00",
            "cloudiness": 26.3
        },
        {
            "date": "2022-08-04T01:00:00.000+00:00",
            "cloudiness": 20.5
        },
        {
            "date": "2022-08-04T02:00:00.000+00:00",
            "cloudiness": 2.3
        }];


        await act(() => { render(<Unison createMap={mapFactory} />); });

        fetchMock.mockResponses(
            JSON.stringify(cloudinessData),
            JSON.stringify({ value: false })
        );

        await screen.findAllByAltText('');

        const marker = screen.getAllByAltText('Marker');


        await act(() => { fireEvent.click(marker[0]); });

        await screen.findByText('Cloudiness data from UCD');


    });
});



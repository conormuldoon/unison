
import "@testing-library/jest-dom/extend-expect";
import { render, screen } from "@testing-library/react";
import TabsComponent from './TabsComponent';
import { createChartFactory } from './ChartComponent';
import { ChartData } from './ChartPopup';

const DLEN = 16;


it('renders without crashing', async () => {

    render(<TabsComponent minMax={false} curVar='Cloudiness' chartFactory={(index) => null} />);
});

it('matches snapshot', () => {


    const { container } = render(<TabsComponent minMax={false} curVar='Cloudiness' chartFactory={(index) => null} />);

    expect(container).toMatchSnapshot();

});



const addChart = async (weatherVariable: string, dataArray: ChartData, zoomDomain: { x: [Date, Date] }) => {


    const chartFactory = createChartFactory(dataArray, zoomDomain, () => { }, weatherVariable, false);

    render(<TabsComponent curVar={weatherVariable} minMax={false} chartFactory={chartFactory} />);


    await screen.findByTestId('chart');

}



it('adds tabs for precipitation', async () => {

    await addChart('Precipitation', [{
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
    }], { x: [new Date('2019-04-01T23:00:00.000+0000'.substring(0, DLEN)), new Date('2019-04-02T10:00:00.000+0000'.substring(0, DLEN))] });



});

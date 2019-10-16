import React from 'react';
import ReactDOM from 'react-dom';
import LeafletMap from './LeafletMap';

import { render,getByTestId,fireEvent,waitForElement} from "react-testing-library";
import fetchMock from 'fetch-mock';


it('renders without crashing', async () => {

  const div = document.createElement('div');

  ReactDOM.render(<LeafletMap/>,div);
  ReactDOM.unmountComponentAtNode(div);
});

it('mathes snapshot', () => {


  const {container} = render(<LeafletMap />);

  expect(container).toMatchSnapshot();

});

it('displays popup', () => {
  const name='UCD';

  // position: [lat, lon]
  const marker=[{name:name,position:[53.308441,-6.223682]}];

  const curVar='Temperature';
  const fromDate='1/2/2018';
  const toDate='7/10/2019';
  const mapCentre=[53.35014, -6.266155];

  fetchMock.get('end:/temperature?location=UCD&fromDate='+fromDate+'&toDate='+toDate, []);

  const markerCallback=jest.fn();
  const {getAllByAltText} = render(<LeafletMap curVar={curVar} mapCentre={mapCentre} markerCallback={markerCallback} marker={marker} fromDate={fromDate} toDate={toDate}/>);

  // Firing click event for marker icon image
  fireEvent.click(getAllByAltText('')[1]);
  expect(markerCallback).toHaveBeenCalledWith(name);

  fetchMock.restore();

});


import Leaflet from 'leaflet';
import PropTypes from 'prop-types';
import React, { useState, useEffect } from 'react';
import { Map, Marker, TileLayer } from 'react-leaflet';

//import ChartPopup from './ChartPopup';


let uri = require('./2000px-Map_marker.png');
if (uri.default) {
  uri = uri.default;
}

const image = new Leaflet.Icon({
  iconUrl: uri,
  iconSize: [30, 46],
})


export type MapMarker = {
  name: string;
  position: [number, number];
}

export function createMapFactory(mapCentre: [number, number]) {

  return function mapFactory(marker: MapMarker[] | null,
    markerClicked: (location: string) => void,
    popupFactory?: (closePopup: () => void) => React.ReactNode): JSX.Element  {

    return <LeafletMap marker={marker}
      markerCallback={markerClicked}
      mapCentre={mapCentre} popupFactory={popupFactory} />;
  }

}

interface MapProps {
  markerCallback: (location: string) => void;
  mapCentre: [number, number];
  marker: MapMarker[] | null;
  popupFactory?: (closePopup: () => void) => React.ReactNode | undefined;

}

/**
 * A component for displaying a Leaflet map and markers for popups for locations where weather data is being tracked.
 * 
 * @component
 * 
 */

function LeafletMap({ markerCallback, mapCentre, marker, popupFactory }: MapProps): JSX.Element  {

  const [popupComponent, setPopupComponent] = useState<React.ReactNode | null>(null);
  const [dragging, setDragging] = useState(false);
  const [display, setDisplay] = useState(false);

  function closePopup() {

    setPopupComponent(null);
    setDragging(true);
    setDisplay(false);

  }

  useEffect(() => {
    if (display && popupFactory) {

      const popupComponent = popupFactory(closePopup);
      //const popupComponent=<ChartPopup uri={"http://localhost:3000/locationCollection/Test/cloudiness?fromDate=23-4-2021&toDate=24-4-2021"} curVar={"Cloudiness"} name={"Testing"} closePopup={closePopup} />
      setPopupComponent(popupComponent);
      setDragging(false);
    }
  }, [popupFactory, display]);


  function mCallback(markerName: string) {

    setDisplay(true);
    markerCallback(markerName);

  }

  return (
    <Map center={mapCentre} zoom={12} dragging={dragging} >
      <TileLayer
        attribution='&copy; <a href="https://osm.org/copyright">OpenStreetMap</a> contributors'
        url='https://{s}.tile.osm.org/{z}/{x}/{y}.png'
      />

      {marker && marker.map((mkr) =>
        <Marker key={mkr.name} position={mkr.position} onClick={mCallback.bind(this, mkr.name)} icon={image} />

      )}
      <div id="marginclickdiv" >
        {popupComponent}
      </div>
    </Map>
  );

}

LeafletMap.propTypes = {


  /** An array of markers to be displayed on the map. Each element of the array specifies the coordinates and location 
   * name associated with a marker.*/
  marker: PropTypes.array,

  /** A function called when a marker is clicked. */
  markerCallback: PropTypes.func,

  /** The latitude/longitude coordinates for the centre of the map. */
  mapCentre: PropTypes.array.isRequired,


  popupFactory: PropTypes.func,
}

export default LeafletMap;

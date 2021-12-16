
import Leaflet from 'leaflet';
import PropTypes from 'prop-types';
import React, { useState, useEffect } from 'react';
import { Map, TileLayer } from 'react-leaflet';



let uri = require('./2000px-Map_marker.png');
if (uri.default) {
  uri = uri.default;
}

const image = new Leaflet.Icon({
  iconUrl: uri,
  iconSize: [30, 46],
})


export type MapMarkerFactory = (component: JSX.Element,
  callback: (name: string) => void,
  image: Leaflet.Icon)
  => JSX.Element;


export const DEFAULT_ZOOM = 12;

export type PopupFactory = ((closePopup: () => void) => React.ReactNode);

export type MapFactory = (marker: MapMarkerFactory[],
  markerClicked: (location: string) => void,
  popupFactory?: PopupFactory) => JSX.Element;


export function createMapFactory(mapCentre: [number, number]): MapFactory {

  return function mapFactory(marker, markerClicked, popupFactory) {

    return <LeafletMap marker={marker}
      markerCallback={markerClicked}
      mapCentre={mapCentre} popupFactory={popupFactory} />;

  }

}

export interface MapProps {

  /**
   * A callback invoked when a marker on the map is clicked.
   */
  markerCallback: (location: string) => void;

  /**
   * A tuple of two numbers for the map's centre.
   */
  mapCentre: [number, number];

  /**
   * An array of markers that are displayed on the map.
   */
  marker: MapMarkerFactory[];

  /**
   * An optional factory that creates chart popup components.
   */
  popupFactory?: PopupFactory;


}

/**
 * A component for displaying a Leaflet map and markers for popups for locations where weather data is being tracked.
 * 
 * @component
 * 
 */

function LeafletMap({ markerCallback, mapCentre, marker, popupFactory }: MapProps): JSX.Element {

  const [popupComponent, setPopupComponent] = useState<React.ReactNode>();
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

      setPopupComponent(popupComponent);
      setDragging(false);
    }
  }, [popupFactory, display]);


  function mCallback(markerName: string) {

    setDisplay(true);
    markerCallback(markerName);

  }

  return (
    <Map center={mapCentre} zoom={DEFAULT_ZOOM} dragging={dragging} >

      <TileLayer
        attribution='&copy; <a href="https://osm.org/copyright">OpenStreetMap</a> contributors'
        url='https://{s}.tile.osm.org/{z}/{x}/{y}.png'
      />

      {marker.map((createMarker) => createMarker(this, mCallback, image))}

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


import Leaflet from 'leaflet';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { Map, Marker, TileLayer } from 'react-leaflet';
import ChartPopup from './ChartPopup';



const image = new Leaflet.Icon({
  iconUrl: require('./2000px-Map_marker.png'),
  iconSize: [30, 46],
})


/**
 * A component for displaying a Leaflet map and markers for popups for locations where weather data is being tracked.
 * 
 * @component
 * 
 */

class LeafletMap extends Component {
  constructor(props) {
    super(props)
    this.state = {

      zoom: 12,
      popupComponent: undefined,
      dragging: true,

    }
  }

  componentDidUpdate = (prevProps) => {

    if (prevProps.curVar !== this.props.curVar || prevProps.location !== this.props.location || prevProps.fromDate !== this.props.fromDate || prevProps.toDate !== this.props.toDate) {
      if (this.state.popupComponent !== undefined) {
        this.closePopup();
        this.addPopup(this.props.location);
      }
    }
  }

  addPopup = (properties) => {
    
    this.props.markerCallback(properties.name);

    const popupComponent = <ChartPopup curVar={this.props.curVar} location={properties} fromDate={this.props.fromDate} toDate={this.props.toDate} closePopup={this.closePopup} />;
    this.setState({ popupComponent: popupComponent, dragging: false });


  }

  markerCallback = (marker) => {

    if (this.state.popupComponent !== undefined) {
      this.closePopup();
    }

    const properties = this.props.featureProperties.get(marker.name);
    this.addPopup(properties);

  }

  closePopup = () => {

    this.setState({ popupComponent: undefined, dragging: true });

  }



  render() {


    return (
      <Map center={this.props.mapCentre} zoom={this.state.zoom} dragging={this.state.dragging} >
        <TileLayer
          attribution='&copy; <a href="https://osm.org/copyright">OpenStreetMap</a> contributors'
          url='https://{s}.tile.osm.org/{z}/{x}/{y}.png'
        />

        {this.props.marker && this.props.marker.map((marker) =>
          <Marker key={marker.name} position={marker.position} onClick={this.markerCallback.bind(this, marker)} icon={image} />

        )}
        <div id="marginclickdiv" onClick={this.closePopup}>
          {this.state.popupComponent}
        </div>
      </Map>
    );
  }
}

LeafletMap.propTypes = {
  /** Specifies the weather variable currently selected. */
  curVar: PropTypes.string,

  /** Specifies the properties of the location selected. */
  location: PropTypes.object,

  /** Specifies the start date for the data that is to be displayed. */
  fromDate: PropTypes.string,

  /** Specifies the end date for the data that is to be displayed. */
  toDate: PropTypes.string,

  /** An array of markers to be displayed on the map. Each element of the array specifies the coordinates and location 
   * name associated with a marker.*/
  marker: PropTypes.array,

  /** A function called when a marker is clicked. */
  markerCallback: PropTypes.func,

  /** The latitude/longitude coordinates for the centre of the map. */
  mapCentre: PropTypes.array.isRequired,

  featureProperties: PropTypes.object
}

export default LeafletMap;

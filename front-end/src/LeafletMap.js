
import Leaflet from 'leaflet';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { Map, Marker, TileLayer} from 'react-leaflet';
import ChartPopup from './ChartPopup';
import { expandLink } from './Util';

//const iu=require('./2000px-Map_marker.png');

const image = new Leaflet.Icon({
   iconUrl: require('./2000px-Map_marker.png').default,
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
      popupComponent: null,
      dragging: true,

    }
  }

  componentDidUpdate = (prevProps) => {

    if (prevProps.curVar !== this.props.curVar || prevProps.linksProperty !== this.props.linksProperty || prevProps.fromDate !== this.props.fromDate || prevProps.toDate !== this.props.toDate) {
      if (this.state.popupComponent !== null) {
        this.closePopup();
        this.addPopup(this.props.linksProperty);
      }
    }
  }

  addPopup = (linksProperty) => {

    this.props.markerCallback(linksProperty.name);

    const uri = expandLink(linksProperty, this.props.curVar, this.props.fromDate, this.props.toDate);

    const popupComponent = <ChartPopup uri={uri} name={linksProperty.name}
      curVar={this.props.curVar} closePopup={this.closePopup} />;
    this.setState({ popupComponent: popupComponent, dragging: false });


  }

  markerCallback = (marker) => {

    if (this.state.popupComponent !== null) {
      this.closePopup();
    }

    const properties = this.props.featureProperties.get(marker.name);
    this.addPopup(properties);

  }

  closePopup = () => {

    this.setState({ popupComponent: null, dragging: true });

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

  featureProperties: PropTypes.object,

  linksProperty: PropTypes.object,
}

export default LeafletMap;

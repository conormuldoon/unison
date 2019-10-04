
import React, { Component  } from 'react';

import { Map, Marker, TileLayer} from 'react-leaflet';
import Leaflet from 'leaflet';
import ChartPopup from './ChartPopup';

import {CENTRE_LAT,CENTRE_LON} from './Constant';



const image = new Leaflet.Icon({
               iconUrl: require('./2000px-Map_marker.png'),
               iconSize:     [30, 46],
           })


/**
 * A component for displaying a Leaflet map and markers for popups for locations where weather data is being tracked.
 */
export default class LeafletMap extends Component {
  constructor() {
    super()
    this.state = {

      lat: CENTRE_LAT,
      lng: CENTRE_LON,
      zoom: 12,
      popupComponent: undefined,
      dragging: true,

    }
  }

  componentDidUpdate = (prevProps, prevState) =>{

    if(prevProps.curVar!==this.props.curVar||prevProps.curLoc!==this.props.curLoc||prevProps.fromDate!==this.props.fromDate||prevProps.toDate!==this.props.toDate){
      if(this.state.popupComponent!==undefined){
        this.closePopup();
        this.addPopup(this.props.curLoc);
      }
    }
  }

  addPopup = (name) =>{
    this.props.markerCallback(name);

    const popupComponent=<ChartPopup varCur={this.props.curVar} location={name} fromDate={this.props.fromDate} toDate={this.props.toDate} closePopup={this.closePopup}/>;
    this.setState({popupComponent:popupComponent,dragging:false});


  }

  markerCallback = (marker) => {

    if(this.state.popupComponent!==undefined){
        this.closePopup();
    }

    this.addPopup(marker.name);

  }

  closePopup = (e) =>{

    this.setState({popupComponent:undefined,dragging:true});

  }



  render() {
    const position = [this.state.lat, this.state.lng];

    return (
      <Map center={position} zoom={this.state.zoom} dragging={this.state.dragging} >
        <TileLayer
          attribution='&copy; <a href="https://osm.org/copyright">OpenStreetMap</a> contributors'
          url='https://{s}.tile.osm.org/{z}/{x}/{y}.png'
        />

        {this.props.marker&&this.props.marker.map((marker) =>
         <Marker key={marker.name} position={marker.position} onClick={this.markerCallback.bind(this,marker)} icon={image}/>

       )}
       <div id="marginclickdiv" onClick={this.closePopup}>
       {this.state.popupComponent}
       </div>
      </Map>
    );
  }
}

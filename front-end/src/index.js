import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import registerServiceWorker from './registerServiceWorker';
import Unison from './Unison';


// Map coordinates
// Dublin
//const mapCentre=[53.35014, -6.266155];

// Oslo
const mapCentre = [59.922326, 10.751560];

ReactDOM.render(<Unison mapCentre={mapCentre} logoLeft={require('./Acclimatize-Logo.png')} logoRight={require('./partners-logos.jpg')} />, document.getElementById('root'));
registerServiceWorker();

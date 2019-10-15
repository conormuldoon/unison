import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Unison from './Unison';
import registerServiceWorker from './registerServiceWorker';


// Map coordinates
// Dublin
//const mapCentre=[53.35014, -6.266155];

// Oslo
const mapCentre=[59.922326, 10.751560];

ReactDOM.render(<Unison mapCentre={mapCentre} />, document.getElementById('root'));
registerServiceWorker();

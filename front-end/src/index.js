import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import { unregister } from './registerServiceWorker';
import Unison from './Unison';
import { createMapFactory } from './closureFactory';

// Map coordinates
// Dublin
const mapCentre = [53.35014, -6.266155];


// Oslo
//const mapCentre = [59.922326, 10.751560];

function createLogo(file) {
    let filePath = file;
    if (filePath.default) {
        filePath = filePath.default;
    }
    return <img id="logoitem" alt="" src={filePath} />
}


const logoLeft = createLogo(require('./Acclimatize-Logo.png'));
const logoRight = createLogo(require('./partners-logos.jpg'));



ReactDOM.render(<Unison createMap={createMapFactory(mapCentre)} logoLeft={logoLeft} logoRight={logoRight} />, document.getElementById('root'));
unregister();


import ReactDOM from 'react-dom';
import './index.css';
import { unregister } from './registerServiceWorker';
import Unison from './Unison';
import { createMapFactory } from './LeafletMap';

// Map coordinates
// Dublin
const mapCentre: [number, number] = [53.35014, -6.266155];


// Oslo
//const mapCentre = [59.922326, 10.751560];

function createLogo(file: { default: string } | string) {

    let filePath;

    if (typeof file === "string") {
        filePath = file;
    } else {
        filePath = file.default;
    }

    return <img id="logoitem" alt="" src={filePath} />
}


const logoLeft = createLogo(require('./Acclimatize-Logo.png'));
const logoRight = createLogo(require('./partners-logos.jpg'));



ReactDOM.render(<Unison createMap={createMapFactory(mapCentre)} logoLeft={logoLeft} logoRight={logoRight} />, document.getElementById('root'));
unregister();

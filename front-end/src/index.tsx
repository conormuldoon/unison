
import { createRoot } from 'react-dom/client';
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

const container = document.getElementById('root');

// eslint-disable-next-line  @typescript-eslint/no-non-null-assertion
const root = createRoot(container!);

root.render(<Unison createMap={createMapFactory(mapCentre)}>
    {logoLeft}
    {logoRight}
</Unison>);
unregister();

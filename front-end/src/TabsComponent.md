TabsComponent example:

```js
import {TODAY} from './Util';

import {useState} from 'react';
const [display,setDisplay] = useState(false);
function toggleDisplay(){
    if(display)setDisplay(false);
    else setDisplay(true);
}

if(display){
    window.scrollTo(0,0);
}

<div style={{"width":"80vw"}}>
    <button onClick={toggleDisplay}>{(display)?"Hide":"Display"}</button>
    
    {display&&<div id="popupdiv" >
        <button onClick={toggleDisplay}>Hide</button>
        <TabsComponent varCur="Precipitation" location="Oslo" fromDate={TODAY} toDate={TODAY}/>
        
    </div>}
</div>
```
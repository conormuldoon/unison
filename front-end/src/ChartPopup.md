ChartPopup example:

```js
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
    
    {!display&&<button onClick={toggleDisplay}>Display popup</button>}
    {display&&<ChartPopup closePopup={toggleDisplay} varCur="Precipitation" location="UCD" fromDate="4/10/2019" toDate="5/10/2019" />}
</div>
```
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

<div>
    
    <button onClick={toggleDisplay}>{(display)?"Hide":"Display"}</button>
    {display&&<ChartPopup closePopup={toggleDisplay} varCur="Precipitation" location="UCD" fromDate="4/10/2019" toDate="5/10/2019" />}
</div>
```
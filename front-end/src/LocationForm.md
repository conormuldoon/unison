LocationForm example:

```js
import {useState} from 'react';
const [display,setDisplay] = useState(false);
function toggleDisplay(){
    if(display)setDisplay(false);
    else setDisplay(true);
}

<div style={{paddingLeft:200}}>
    <LocationForm display={display} toggleDisplay ={toggleDisplay} />
</div>
```
RemoveForm example:

```js
import {useState} from 'react';
const [display,setDisplay] = useState(false);
function toggleDisplay(){
    if(display)setDisplay(false);
    else setDisplay(true);
}

<div style={{paddingLeft:200}}>
    <RemoveForm display={display} toggleDisplay ={toggleDisplay} location="Oslo" />
</div>
```
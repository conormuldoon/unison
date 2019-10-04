ChartComponent example:

```js

const data=[{"date":"2019-02-23T19:00:00.000+0000","precipitation":{"value":0.0,"minvalue":0.0,"maxvalue":0.0}},{"date":"2019-02-23T20:00:00.000+0000","precipitation":{"value":0.5,"minvalue":0.0,"maxvalue":0.7}},{"date":"2019-02-23T21:00:00.000+0000","precipitation":{"value":0.6,"minvalue":0.0,"maxvalue":0.6}},{"date":"2019-02-23T22:00:00.000+0000","precipitation":{"value":3.0,"minvalue":0.0,"maxvalue":0.4}},{"date":"2019-02-23T23:00:00.000+0000","precipitation":{"value":0.2,"minvalue":0.0,"maxvalue":0.5}},{"date":"2019-02-26T18:00:00.000+0000","precipitation":{"value":0.7,"minvalue":0.0,"maxvalue":0.8}},{"date":"2019-02-25T12:00:00.000+0000","precipitation":{"value":0.3,"minvalue":0.0,"maxvalue":0.5}}];


<ChartComponent data={data} yVal={'precipitation.value'} yLabel={'Millimetres'} />


```  

import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';
import { IoMdClose } from "react-icons/io";
import './App.css';
import { PRECIP } from './Constant';
import TabsComponent from './TabsComponent';
import { expandLink, chartText } from './Util';


const DLEN = 16;

/**
 * A popup that displays a TabsComponent. Once mounted, it connects to the back-end to obtain data
 * for the from date, to date, location, and weather variable selected.
 * 
 * @component
 */
function ChartPopup(props) {

  const [data, setData] = useState(undefined);
  const [zoomDomain, setZoomDomain] = useState(undefined);
  const [minMax, setMinMax] = useState(false);

  useEffect(() => {

    let active = true;

    async function obtainData() {

      const uri = expandLink(props.linksProperty, props.location, props.curVar, props.fromDate, props.toDate);

      const response = await fetch(uri, {
        method: 'GET',
        headers: new Headers({
          'Content-Type': 'application/json'
        })
      });
      const dataArray = await response.json();

      if (active) {
        const n = dataArray.length;

        for (let i = 0; i < n; i++) {
          const dateS = dataArray[i].date;
          dataArray[i].date = new Date(dateS.substring(0, DLEN));

        }

        if (n > 0) {

          const fd = dataArray[0].date;
          const td = dataArray[dataArray.length - 1].date;

          if (props.curVar === PRECIP && dataArray[0].precipitation.minvalue !== undefined) {

            setMinMax(true);
          } else {
            setMinMax(false);
          }

          setData(dataArray);
          setZoomDomain({ x: [fd, td] });
        } else {

          setData(undefined);
          setZoomDomain(undefined);
          setMinMax(false);
        }
      }

    }

    obtainData();
    const cancel = () => active = false;
    return cancel;

  }, [props.linksProperty, props.curVar, props.location, props.fromDate, props.toDate]);

  function handleClick(e) {
    e.stopPropagation();
  }

  const vc = chartText(props.curVar);

  return (
    <div id="popupdiv" data-testid='chart-div' onClick={handleClick}>
      <div id="iicon">
        <IoMdClose onClick={props.closePopup} size={20} color="rgb(192, 57, 43)" />

      </div>
      <center>
        {vc} data from {props.location.name.trim()}

        {data && <TabsComponent curVar={props.curVar} data={data} zoomDomain={zoomDomain} minMax={minMax}
          setZoomDomain={setZoomDomain} />}

      </center>
    </div>
  );


}

ChartPopup.propTypes = {
  /** Specifies the weather variable currently selected. */
  curVar: PropTypes.string.isRequired,

  /** Specifies the properties of the location selected. */
  location: PropTypes.object.isRequired,

  /** Specifies the start date for the data that is to be displayed. */
  fromDate: PropTypes.string.isRequired,

  /** Specifies the end date for the data that is to be displayed. */
  toDate: PropTypes.string.isRequired,

  /** Called when the close icon is clicked. */
  closePopup: PropTypes.func.isRequired,

  linksProperty: PropTypes.object.isRequired,
}

export default ChartPopup;


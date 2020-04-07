import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';
import { IoMdClose } from "react-icons/io";
import './App.css';
import { API, PRECIP } from './Constant';
import TabsComponent from './TabsComponent';
import { varMapping } from './Util';


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


      const variRequest = varMapping(props.curVar);


      const loc = props.location.trim();
      const request = API + '/' + variRequest + '?location=' + loc + "&fromDate=" + props.fromDate + "&toDate=" + props.toDate;

      let response = await fetch(request);
      let dataArray = await response.json();

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

  }, [props.curVar, props.location, props.fromDate, props.toDate]);

  function handleClick(e) {
    e.stopPropagation();
  }

  // Changing second word in current variable to lower case if present
  let vca = props.curVar.split(' ');
  let vc = vca[0];
  if (vca.length > 1) {
    vca[1] = vca[1].toLowerCase();
    vc += ' ' + vca[1];
  }

  return (
    <div id="popupdiv" data-testid='chart-div' onClick={handleClick}>
      <div id="iicon">
        <IoMdClose onClick={props.closePopup} size={20} color="rgb(192, 57, 43)" />

      </div>
      <center>
        {vc} data from {props.location.trim()}

        {data && <TabsComponent curVar={props.curVar} data={data} zoomDomain={zoomDomain} minMax={minMax}
          setZoomDomain={setZoomDomain} />}

      </center>
    </div>
  );

}

ChartPopup.propTypes = {
  /** Specifies the weather variable currently selected. */
  curVar: PropTypes.string.isRequired,

  /** Sepecifies the location selected. */
  location: PropTypes.string.isRequired,

  /** Specifies the start date for the data that is to be displayed. */
  fromDate: PropTypes.string.isRequired,

  /** Specifies the end date for the data that is to be displayed. */
  toDate: PropTypes.string.isRequired,

  /** Called when the close icon is clicked. */
  closePopup: PropTypes.func.isRequired,

}

export default ChartPopup;


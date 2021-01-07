import PropTypes from 'prop-types';
import React, { useEffect, useState } from 'react';
import { IoMdClose } from "react-icons/io";
import './App.css';
import { PRECIP } from './Constant';
import TabsComponent from './TabsComponent';
import { chartText } from './Util';
import { EmailShareButton, EmailIcon, WhatsappShareButton, WhatsappIcon, TelegramShareButton, TelegramIcon } from "react-share";


const DLEN = 16;

const SSIZE = 24;
/**
 * A popup that displays a TabsComponent. Once mounted, it connects to the back-end to obtain data
 * for the from date, to date, location, and weather variable selected.
 * 
 * @component
 */
function ChartPopup({ uri, curVar, name, closePopup }) {

  const [data, setData] = useState(null);
  const [zoomDomain, setZoomDomain] = useState(null);
  const [minMax, setMinMax] = useState(false);

  useEffect(() => {

    let active = true;

    async function obtainData() {

      const response = await fetch(uri, {
        method: 'GET',
        credentials: 'omit',
        headers: new Headers({
          'Accept': 'application/json'
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

          if (curVar === PRECIP && dataArray[0].precipitation.minvalue !== undefined) {

            setMinMax(true);
          } else {
            setMinMax(false);
          }

          setData(dataArray);
          setZoomDomain({ x: [fd, td] });
        } else {

          setData(null);
          setZoomDomain(null);
          setMinMax(false);
        }
      }

    }

    obtainData();
    const cancel = () => active = false;
    return cancel;

  }, [uri, curVar]);

  function handleClick(e) {
    e.stopPropagation();
  }

  const vc = chartText(curVar);

  const subject = vc + ' data from ' + name.trim();

  return (
    <div id="popupdiv" data-testid='chart-div' onClick={handleClick}>


      <div id="iicon">
        <IoMdClose onClick={closePopup} size={20} color="rgb(192, 57, 43)" />


        <EmailShareButton
          url={uri}
          subject={subject}
        >
          <EmailIcon size={SSIZE} />
        </EmailShareButton>

        <WhatsappShareButton
          url={uri}
          title={subject}
        >
          <WhatsappIcon size={SSIZE} />
        </WhatsappShareButton>

        <TelegramShareButton
          url={uri}
          title={subject}
        >
          <TelegramIcon size={SSIZE} />
        </TelegramShareButton>

      </div>


      <center>

        <div style={{ marginTop: 10, marginBottom: 20 }}>
          {subject}
        </div>


        {data && <TabsComponent curVar={curVar} data={data} zoomDomain={zoomDomain} minMax={minMax}
          setZoomDomain={setZoomDomain} />}

      </center>

    </div>
  );


}

ChartPopup.propTypes = {

  /** Specifies the weather variable currently selected. */
  curVar: PropTypes.string.isRequired,

  /** Called when the close icon is clicked. */
  closePopup: PropTypes.func.isRequired,

  name: PropTypes.string.isRequired,

  uri: PropTypes.string.isRequired,
}

export default ChartPopup;


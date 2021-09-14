
import React, { useEffect, useState, MouseEvent } from 'react';
import { IoMdClose } from "react-icons/io";
import './App.css';
import { PRECIP } from './Constant';
import TabsComponent from './TabsComponent';
import { createChartFactory } from './ChartComponent';
import { chartText } from './Util';
import { EmailShareButton, EmailIcon, WhatsappShareButton, WhatsappIcon, TelegramShareButton, TelegramIcon } from 'react-share';
import { DomainPropType } from 'victory'

const DLEN = 16;

const SSIZE = 24;


export function createPopupFactory(uri: string, curVar: string, name: string) {

  return function popupFactory(closePopup: () => void): JSX.Element {

    return <ChartPopup uri={uri} curVar={curVar} name={name} closePopup={closePopup} />

  }
}

export type ChartData = Record<string, unknown>[];

interface PopupProps {
  uri: string;
  curVar: string;
  name: string;
  closePopup: () => void;
}
/**
 * A popup that displays a TabsComponent. Once mounted, it connects to the back-end to obtain data
 * for the from date, to date, location, and weather variable selected.
 * 
 * @component
 */
function ChartPopup({ uri, curVar, name, closePopup }: PopupProps): JSX.Element {

  const [data, setData] = useState<ChartData>([]);
  const [zoomDomain, setZoomDomain] = useState<DomainPropType>({ x: [0, 1] });
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

          setData([]);
          setZoomDomain({ x: [0, 1] });
          setMinMax(false);
        }
      }

    }

    obtainData();
    const cancel = () => { active = false };
    return cancel;

  }, [uri, curVar]);

  function handleClick(e: MouseEvent) {
    e.stopPropagation();
  }

  const vc = chartText(curVar);

  const subject = vc + ' data from ' + name.trim();

  const chartFactory = createChartFactory(data, zoomDomain, setZoomDomain, curVar, minMax);

  return (
    <div id="popupdiv" data-testid='chart-div' onClick={handleClick} style={{ textAlign: 'center' }}>


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


      <div style={{ marginTop: 10, marginBottom: 20, textAlign: 'center' }}>
        {subject}
      </div>


      {data && <TabsComponent curVar={curVar} minMax={minMax} chartFactory={chartFactory} />}



    </div>
  );


}


export default ChartPopup;


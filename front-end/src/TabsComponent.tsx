import PropTypes from 'prop-types';
import React from 'react';
import { Tab, TabList, TabPanel, Tabs } from 'react-tabs';
import "react-tabs/style/react-tabs.css";
import { CL, HIGH, HPER, LOW, LPER, MEDIAN, MEDIUM } from './Constant';

interface TabsProps {
  minMax: boolean;
  curVar: string;
  chartFactory: (index?: number) => React.ReactNode;
}
/**
 * A component that displays a number of tabs for ChartComponents for the phenomenom being tracked. 
 * For exmaple, for cloud level, there is a tab for high, medium, and low level clouds. Otherwise,
 * only a single ChartComponent is displayed.
 *  
 * @component
 */
function TabsComponent({ minMax, curVar, chartFactory }:
  TabsProps): JSX.Element {

  let tabs;

  if (minMax) {

    tabs = [LPER, MEDIAN, HPER];

  } else if (curVar === CL) {

    tabs = [LOW, MEDIUM, HIGH];

  }

  function tabsPanel(index: number) {
    const tChart = chartFactory(index);
    return (<TabPanel >
      <div id="tabdiv" data-testid='chart' >
        {tChart}
      </div>
    </TabPanel>
    );
  }

  let chart;
  if (!tabs) {
    chart = chartFactory()
  }

  return (<div id="chartdiv">
    {tabs && <Tabs id="tabscomponent" defaultIndex={1}>
      <TabList >
        <Tab>{tabs[0]}</Tab>
        <Tab>{tabs[1]}</Tab>
        <Tab>{tabs[2]}</Tab>
      </TabList>

      {tabsPanel(0)}
      {tabsPanel(1)}
      {tabsPanel(2)}

    </Tabs>}

    {!tabs && <div data-testid='chart' id="singlevar">
      {chart}
    </div>
    }
  </div>
  );

}


TabsComponent.propTypes = {

  chartFactory: PropTypes.func.isRequired,

  /** The current weather variable selected. */
  curVar: PropTypes.string.isRequired,

  /** Determines wheather to display tabs for the precipitation weather variable. This is needed in that with
   * some locations and models only provide a single preciption value (no minimum or maximum).
   */
  minMax: PropTypes.bool,


}

export default TabsComponent;

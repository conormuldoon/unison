import React from 'react';
import LeafletMap from './LeafletMap';
import LocationForm from './LocationForm';
import RemoveComponent from './RemoveComponent';
import ChartComponent from './ChartComponent';

export function createMapFactory(mapCentre) {

    return function mapFactory(marker, curVar, locationMap, markerClicked, fromDate, toDate, curLoc) {
        return <LeafletMap marker={marker} curVar={curVar} featureProperties={locationMap}
            markerCallback={markerClicked} fromDate={fromDate} toDate={toDate}
            mapCentre={mapCentre} linksProperty={curLoc} />;
    }

}

export function createLocationFactory(obtainData, selfRef, containsRef) {

    return function locationFactory(toggleDisplayAdd, displayAdd, hideAdd) {
        return <LocationForm obtainData={obtainData} toggleDisplay={toggleDisplayAdd}
            display={displayAdd} hideDisplay={hideAdd} selfRef={selfRef} containsRef={containsRef} />;
    }
}

export function createRemoveFactory(obtainData, href, name) {

    return function removeFactory(hideDisplay) {
        return <RemoveComponent obtainData={obtainData}
            hideDisplay={hideDisplay}
            href={href}
            name={name}
        />;
    }
}

export function createChartFactory(data, zoomDomain, handleZoom, curVar, minMax) {

    return function chartFactory(index) {
    
        return <ChartComponent data={data} zoomDomain={zoomDomain} index={index}
            handleZoom={handleZoom}
            curVar={curVar} minMax={minMax} />
    }
}
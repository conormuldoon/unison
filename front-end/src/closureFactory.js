import React from 'react';
import LeafletMap from './LeafletMap';
import LocationForm from './LocationForm';
import RemoveComponent from './RemoveComponent';

export function createMapFactory(mapCentre) {

    return function (marker, curVar, locationMap, markerClicked, fromDate, toDate, curLoc) {
        return <LeafletMap marker={marker} curVar={curVar} featureProperties={locationMap}
            markerCallback={markerClicked} fromDate={fromDate} toDate={toDate}
            mapCentre={mapCentre} linksProperty={curLoc} />;
    }

}

export function createLocationFactory(obtainData, collectionModel) {

    return function (toggleDisplayAdd, displayAdd, hideAdd) {
        return <LocationForm obtainData={obtainData} toggleDisplay={toggleDisplayAdd}
            display={displayAdd} hideDisplay={hideAdd} collectionModel={collectionModel} />;
    }
}

export function createRemoveFactory(obtainData, linksProperty) {

    return function (hideAdd) {
        return <RemoveComponent obtainData={obtainData}
            hideDisplay={hideAdd}
            linksProperty={linksProperty} />;
    }
}
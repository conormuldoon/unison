import React from 'react';
import ReactDOM from 'react-dom';
import RemoveForm from './RemoveForm';
import { render, cleanup, fireEvent,waitForElement,rerender } from "react-testing-library";

import "@testing-library/jest-dom/extend-expect";
import fetchMock from 'fetch-mock';


it('renders without crashing', async () => {

  const div = document.createElement('div');

  ReactDOM.render(<RemoveForm location='UCD'
    obtainData={()=>{}} hideDisplay={()=>{}} toggleDisplay={()=>{}} display={true}/>, div);
  ReactDOM.unmountComponentAtNode(div);
});

it('mathes snapshot', () => {


  const {container} = render(<RemoveForm display={true} location='UCD' obtainData={()=>{}} hideDisplay={()=>{}} toggleDisplay={()=>{}} display={true}/>);

  expect(container).toMatchSnapshot();

});


it('handles remove location', async (done) =>{
  fetchMock.post('end:/deleteLocation', '1');
  const location='UCD';

  jest.spyOn(window, 'alert').mockImplementation(() => {});
  const hideDisplay=jest.fn();
  
  const obtainData=() =>{
    expect(hideDisplay).toHaveBeenCalledTimes(1);
    expect(alert).toBeCalledWith(location+' removed');
    done();
  };

  const component=<RemoveForm location={location} display={true} obtainData={obtainData} hideDisplay={hideDisplay} toggleDisplay={()=>{}} />;
  const {getByText} = render(component);

  fireEvent.click(getByText('Remove ' + location));

  fetchMock.restore();


});


const testRemove = async(done,retVal,message) =>{
  fetchMock.post('end:/deleteLocation', retVal);
  const location='UCD';

  jest.spyOn(window, 'alert').mockImplementation((message) => {
    expect(message).toEqual(message);
    done();
  });


  const component=<RemoveForm location={location} display={true} obtainData={()=>{}} toggleDisplay={()=>{}} hideDisplay={()=>{}} />;
  const {getByText} = render(component);

  fireEvent.click(getByText('Remove ' + location));

  fetchMock.restore();
}

it('displays that the location is not present', async (done) =>{
  testRemove(done,'0','Location does not exist');

});

it("displays don't have permssion to remove location", async (done) =>{
  testRemove(done,'3','You do not have permission to delete this location');

});

it('Invalid login details', async (done) =>{
  testRemove(done,'2','Incorrect user name or password');

});

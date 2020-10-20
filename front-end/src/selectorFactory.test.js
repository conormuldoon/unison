import ReactDOM from 'react-dom';
import { render } from "react-testing-library";
import createSelector from './selectorFactory';


const dateSelector = createSelector('Test Label', '23/10/2019', () => { });

it('renders without crashing', () => {
  const div = document.createElement('div');


  ReactDOM.render(dateSelector, div);
  ReactDOM.unmountComponentAtNode(div);
});



it('mathes snapshot', () => {


  const { container } = render(dateSelector);

  expect(container).toMatchSnapshot();

});

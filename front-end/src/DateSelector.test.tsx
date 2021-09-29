import { render } from "@testing-library/react";
import DateSelector from './DateSelector';


function handle(d: Date) {

}
const dateSelector = <DateSelector label={'Test Label'} dateValue={'14/9/2021'} handleDayChange={handle} />;

it('renders without crashing', () => {

  render(dateSelector);
});



it('mathes snapshot', () => {


  const { container } = render(dateSelector);

  expect(container).toMatchSnapshot();

});

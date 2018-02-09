import { combineReducers } from 'redux';

import count from './count';
import calc from './calc';

export default combineReducers({
    count,
    calc
});

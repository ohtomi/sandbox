import { combineReducers } from 'redux';

import count from './count/index';
import calc from './calc/index';

export default combineReducers({
    count,
    calc
});

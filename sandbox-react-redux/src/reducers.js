import { combineReducers } from 'redux';

import count from './count/index';
import calc from './calc/index';
import misc from './misc/index'
import routing from './routing/index';

export default combineReducers({
    count,
    calc,
    misc,
    routing
});

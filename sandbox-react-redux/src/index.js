import React from 'react';
import ReactDOM from 'react-dom';
import AppContainer from './components/AppContainer';

import { Provider } from 'react-redux';
import { createStore, applyMiddleware, compose } from 'redux';
import thunkMiddleware from 'redux-thunk';
import logger from 'redux-logger'
import { createHashHistory } from 'history';
import middleware from './middleware';
import enhancer from './enhancer';
import reducers from './reducers';

import './index.css';

import registerServiceWorker from './registerServiceWorker';

let history = createHashHistory();
let store = createStore(reducers, compose(applyMiddleware(middleware(history), thunkMiddleware, logger), enhancer()));

ReactDOM.render(
    <Provider store={store}>
        <AppContainer history={history} />
    </Provider>,
    document.getElementById('root')
);

registerServiceWorker();

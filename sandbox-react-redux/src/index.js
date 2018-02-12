import React from 'react';
import ReactDOM from 'react-dom';
import AppContainer from './components/AppContainer';

import { Provider } from 'react-redux';
import { createStore, applyMiddleware, compose } from 'redux';
import thunkMiddleware from 'redux-thunk';
import logger from 'redux-logger'
import enhancer from './enhancer';
import reducers from './reducers';

import './index.css';

import registerServiceWorker from './registerServiceWorker';

let store = createStore(reducers, compose(applyMiddleware(thunkMiddleware, logger), enhancer()));

ReactDOM.render(
    <Provider store={store}>
        <AppContainer />
    </Provider>,
    document.getElementById('root')
);

registerServiceWorker();

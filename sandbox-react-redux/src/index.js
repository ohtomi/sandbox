import React from 'react';
import ReactDOM from 'react-dom';
import AppContainer from './components/AppContainer';

import { Provider } from 'react-redux';
import { createStore, applyMiddleware } from 'redux';
import thunkMiddleware from 'redux-thunk';
import reducers from './reducers';

import './index.css';

import registerServiceWorker from './registerServiceWorker';

let store = createStore(reducers, applyMiddleware(thunkMiddleware));

ReactDOM.render(
    <Provider store={store}>
        <AppContainer />
    </Provider>,
    document.getElementById('root')
);

registerServiceWorker();

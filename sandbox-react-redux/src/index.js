import React from 'react'
import ReactDOM from 'react-dom'
import AppContainer from './components/AppContainer'

import { Provider } from 'react-redux'
import { createStore, applyMiddleware, compose } from 'redux'
import thunkMiddleware from 'redux-thunk'
import logger from 'redux-logger'
import { createHashHistory } from 'history'
import middleware from './middleware'
import enhancer from './enhancer'
import reducers from './reducers'

import UniversalRouter from 'universal-router'
import routes from './routes'

import './index.css'

import registerServiceWorker from './registerServiceWorker'

let history = createHashHistory()
let initial = {
    routing: {
        pathname: history.location.pathname,
        search: history.location.search,
        hash: history.location.search
    }
}
let store = createStore(reducers, initial, compose(applyMiddleware(middleware(history), thunkMiddleware, logger), enhancer()))

const router = new UniversalRouter(routes)
const renderer = (location) => {
    router.resolve(location)
        .then((route) => {
            ReactDOM.render(
                <Provider store={store}>
                    <AppContainer routes={routes} history={history} route={route} />
                </Provider>,
                document.getElementById('root')
            )
        })
}

history.listen((location) => renderer(location))
renderer(history.location)

registerServiceWorker()

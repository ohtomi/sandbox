import React from 'react'
import ReactDOM from 'react-dom'
import AppContainer from './components/AppContainer'

import { Provider } from 'react-redux'
import store from './store'
import router from './router'
import history from './history'

import './index.css'

import registerServiceWorker from './registerServiceWorker'

const renderer = (location) => {
    router.resolve(location)
        .then((route) => {
            ReactDOM.render(
                <Provider store={store}>
                    <AppContainer history={history} route={route} />
                </Provider>,
                document.getElementById('root')
            )
        })
}

history.listen((location) => renderer(location))
renderer(history.location)

if (module.hot) {
    module.hot.accept('./router.js', () => {
        renderer(history.location)
    })
}

registerServiceWorker()

import React from 'react'
import Header from '../atoms/Header'
import Count from '../atoms/Count'
import Calc from '../atoms/Calc'

import './App.css'

const App = (props) => {
    const { state: { misc: { locked } }, route: { Component } } = props
    return (
        <div className={locked ? 'App App-locked' : 'App'}>
            <Header {...props} />
            <p className="App-intro">
                <Count {...props} />
            </p>
            <p className="App-intro">
                <Calc {...props} />
            </p>
            <p className="App-intro">
                <Component {...props} />
            </p>
        </div>
    )
}

export default App

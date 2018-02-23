import React from 'react'

import './App.css'

const App = (props) => {
    const { route: { Component } } = props
    return (
        <Component {...props} />
    )
}

export default App

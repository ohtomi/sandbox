import React from 'react'
import Layout from './Layout'

import './App.css'

const App = (props) => {
    const { state: { misc: { locked } } } = props
    return (
        <div className={locked ? 'App App-locked' : 'App'}>
            <Layout {...props} />
        </div>
    )
}

export default App

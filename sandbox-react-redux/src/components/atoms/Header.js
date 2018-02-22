import React from 'react'

import logo from './logo.svg'

const Header = ({ state, actions }) => {
    return (
        <header className="App-header">
            <img src={logo} className={state.misc.locked ? 'App-logo App-locked' : 'App-logo'} alt="logo" />
            <h1 className="App-title">Welcome to React-Redux</h1>
            <button disabled={state.misc.locked} onClick={() => { actions.misc.lockUi() }}>lock UI</button>
            <button disabled={!state.misc.locked} onClick={() => { actions.misc.unlockUi() }}>unlock UI</button>
        </header>
    )
}

export default Header

import React from 'react'

import logo from './logo.svg'

const Header = ({ locked, lockUi, unlockUi }) => {
    return (
        <header className="App-header">
            <img src={logo} className={locked ? 'App-logo App-locked' : 'App-logo'} alt="logo" />
            <h1 className="App-title">Welcome to React-Redux</h1>
            <button disabled={locked} onClick={() => { lockUi() }}>lock UI</button>
            <button disabled={!locked} onClick={() => { unlockUi() }}>unlock UI</button>
        </header>
    )
}

export default Header

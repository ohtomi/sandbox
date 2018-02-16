import React from 'react';

import logo from './logo.svg';
import './App.css';

import { Op } from '../calc/constants';

const App = ({ state, actions, route: { Component } }) => {
    return (
        <div className={state.misc.locked ? 'App App-locked' : 'App'}>
            <header className="App-header">
                <img src={logo} className={state.misc.locked ? 'App-logo App-locked' : 'App-logo'} alt="logo" />
                <h1 className="App-title">Welcome to React-Redux</h1>
                <button disabled={state.misc.locked} onClick={() => { actions.misc.lockUi() }}>lock UI</button>
                <button disabled={!state.misc.locked} onClick={() => { actions.misc.unlockUi() }}>unlock UI</button>
            </header>
            <p className="App-intro">
                current value: {state.count.value}<br />
                <button onClick={() => { actions.count.incrementAsync() }}>increment value</button><br />
                <button onClick={() => { actions.count.decrement() }}>decrement value</button>
            </p>
            <p className="App-intro">
                <input type="number" value={state.calc.answer} />
                <span className="App-margin">=</span>
                <input type="number" value={state.calc.arg1} onChange={(ev) => { actions.calc.update1(ev.target.value) }} />
                <select className="App-margin" value={state.calc.op} onChange={(ev) => { actions.calc.selectOp(ev.target.value) }}>
                    <option value={Op.ADD}> + </option>
                    <option value={Op.SUBTRACT}> - </option>
                </select>
                <input type="number" value={state.calc.arg2} onChange={(ev) => { actions.calc.update2(ev.target.value) }} />
            </p>
            <p className="App-intro">
                <Component {...{ state, actions }} />
            </p>
        </div>
    );
}

export default App;

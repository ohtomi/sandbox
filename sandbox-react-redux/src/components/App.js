import React from 'react';

import logo from './logo.svg';
import './App.css';

import { Op } from '../calc/constants';

const App = ({ state: { count: { value }, calc: { op, answer, arg1, arg2 } }, actions: { count: { incrementAsync, decrement }, calc } }) => {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1 className="App-title">Welcome to React-Redux</h1>
      </header>
      <p className="App-intro">
        current value: {value}<br />
        <button onClick={() => { incrementAsync() }}>increment value</button><br />
        <button onClick={() => { decrement() }}>decrement value</button>
      </p>
      <p className="App-intro">
        <input type="number" value={answer} />
        <span className="App-margin">=</span>
        <input type="number" value={arg1} onChange={(ev) => { calc.update1(ev.target.value) }} />
        <select className="App-margin" value={op} onChange={(ev) => { calc.selectOp(ev.target.value) }}>
          <option value={Op.ADD}> + </option>
          <option value={Op.SUBTRACT}> - </option>
        </select>
        <input type="number" value={arg2} onChange={(ev) => { calc.update2(ev.target.value) }} />
      </p>
    </div>
  );
};

export default App;

import React from 'react';

import logo from './logo.svg';
import './App.css';

const App = ({ state: { count: { value } }, actions: { count: { incrementAsync, decrement } } }) => {
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
    </div>
  );
};

export default App;

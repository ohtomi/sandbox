import React from 'react';
import { connect, } from 'react-redux';
import { bindActionCreators } from 'redux';
import logo from './logo.svg';
import './App.css';
import * as Actions from './Actions';

const App = ({ state, increment, decrement }) => {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <h1 className="App-title">Welcome to React-Redux</h1>
      </header>
      <p className="App-intro">
        current value: {state.value}<br />
        <button onClick={() => { increment() }}>increment value</button><br />
        <button onClick={() => { decrement() }}>decrement value</button>
      </p>
    </div>
  );
}

export default connect(
  state => { return { state } },
  dispatch => { return { ...bindActionCreators(Actions, dispatch) } }
)(App);

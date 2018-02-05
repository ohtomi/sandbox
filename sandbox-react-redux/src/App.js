import React, { Component } from 'react';
import { connect } from 'react-redux';
import logo from './logo.svg';
import './App.css';
import { Increment, Decrement } from './Actions';

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Welcome to React-Redux</h1>
        </header>
        <p className="App-intro">
          current value: {this.props.state.value}<br />
          <button onClick={() => { this.props.dispatch(Increment()) }}>increment value</button><br />
          <button onClick={() => { this.props.dispatch(Decrement()) }}>decrement value</button>
        </p>
      </div>
    );
  }
}

export default connect((state) => { return { state } })(App);

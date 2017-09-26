import React from 'react';
import RX from 'reactxp';
// import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';

RX.App.initialize(true, true);
RX.UserInterface.setMainView(<App />);
// ReactDOM.render(<App />, document.getElementById('root'));
registerServiceWorker();

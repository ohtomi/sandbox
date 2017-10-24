import EventEmitter = require('events');
import RX = require('reactxp');
import App = require('./App');

const emitter = new EventEmitter();

// b-5. dump message
window.notifyBackgroundMessage = (message) => {
    emitter.emit('from-devtools', message);
};

RX.App.initialize(true, true);
RX.UserInterface.setMainView(<App emitter={emitter} />);

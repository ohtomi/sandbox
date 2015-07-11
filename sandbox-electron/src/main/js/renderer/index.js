// index.js
(function() {

'use strict';

var menuBuilder = require('../js/renderer/menuBuilder.js');
menuBuilder.build();

var misc = require('../js/renderer/misc.js');
var button = document.getElementById('capture-button');
button.addEventListener('click', misc.captureThisWindow);

var ReactMain = require('../js/renderer/components/ReactMain.js');
ReactMain.render();

})();

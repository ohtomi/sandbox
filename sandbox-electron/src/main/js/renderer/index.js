// index.js
(function() {

'use strict';

var menuBuilder = require('../js/renderer/menuBuilder.js');
menuBuilder.build();

var ReactMain = require('../js/renderer/component/ReactMain.js');
ReactMain.render();

})();

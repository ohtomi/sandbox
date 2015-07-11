// misc.js
(function() {

'use strict';

function captureThisWindow() {
  var remote = require('remote');
  remote.getCurrentWindow().capturePage(function(image) {
    var buf = image.toPng();
    remote.require('fs').writeFile('/tmp/screenshot.png', buf, function(err) {
      console.log(err);
    });
  });
}

module.exports = {
  captureThisWindow: captureThisWindow
};

})();

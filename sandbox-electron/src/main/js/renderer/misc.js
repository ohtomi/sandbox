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

function buildCellLabel(x, y) {
  var s = '';
  x += 1;
  while (x > 0) {
    s = String.fromCharCode((x - 1) % 26 + 65) + s;
    x = Math.floor((x - 1) / 26);
  }
  s = s + (y + 1);
  return s;
}

module.exports = {
  captureThisWindow: captureThisWindow,
  buildCellLabel: buildCellLabel
};

})();

// ReactMain.jsx
(function() {

'use strict';

function render() {
  var React = require('react');
  var ExcelView = require('./ExcelView.js');

  React.render(<ExcelView />, document.getElementById('content'));
}

module.exports = {
  render: render
};

})();

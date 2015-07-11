// ReactMain.jsx
(function() {

'use strict';

function render() {
  var React = require('react');
  var ExcelView = require('./ExcelView.js');

  var cells = [
    { x: 1, y: 2, value: 'We are using ...' }
  ];

  React.render(<ExcelView cells={cells} />, document.getElementById('content'));
}

module.exports = {
  render: render
};

})();

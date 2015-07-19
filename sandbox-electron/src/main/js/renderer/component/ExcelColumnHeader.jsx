// ExcelColumnHeader.jsx
(function() {

'use strict';

var React = require('react');

function toExcelNotation(n) {
    var s = '';
    while (n > 0) {
        s = String.fromCharCode((n - 1) % 26 + 65) + s;
        n = Math.floor((n - 1) / 26);
    }
    return s;
}

var ExcelColumnHeader = React.createClass({

  render: function() {
    var rows = [];
    for (var i = 0; i < 100; i++) {
      rows.push(<td key={i}>{toExcelNotation(i + 1)}</td>);
    }
    return (
      <table className="jg-header column">
        <tr>
          {rows}
        </tr>
      </table>
    );
  }

});

module.exports = ExcelColumnHeader;

})();

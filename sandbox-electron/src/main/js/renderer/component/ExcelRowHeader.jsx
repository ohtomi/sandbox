// ExcelRowHeader.jsx
(function() {

'use strict';

var React = require('react');

var ExcelRowHeader = React.createClass({

  render: function() {
    var rows = [];
    rows.push(<tr key={'◢'}><td>◢</td></tr>);
    for (var i = 0; i < 100; i++) {
      rows.push(<tr key={i}><td>{i + 1}</td></tr>);
    }
    return (
      <table className="jg-header row">
        {rows}
      </table>
    );
  }

});

module.exports = ExcelRowHeader;

})();

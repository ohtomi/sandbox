// ExcelCell.jsx
(function() {

'use strict';

var React = require('react');

var ExcelCell = React.createClass({

  render: function() {
    return (
      <div data-x={this.props.cell.x} data-y={this.props.cell.y}>
        {this.props.cell.value}
      </div>
    );
  }

});

module.exports = ExcelCell;

})();

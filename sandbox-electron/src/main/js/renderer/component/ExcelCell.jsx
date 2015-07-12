// ExcelCell.jsx
(function() {

'use strict';

var React = require('react');
var ExcelStore = require('../store/ExcelStore.js');

var ExcelCell = React.createClass({

  render: function() {
    var style = {
      position: 'absolute',
      marginLeft: (this.props.cell.x * 20) + 'px',
      marginTop: (this.props.cell.y * 20) + 'px'
    };
    return (
      <div data-x={this.props.cell.x} data-y={this.props.cell.y} style={style}>
        {this.props.cell.func ? this.props.cell.func(ExcelStore) : this.props.cell.value}
      </div>
    );
  }

});

module.exports = ExcelCell;

})();

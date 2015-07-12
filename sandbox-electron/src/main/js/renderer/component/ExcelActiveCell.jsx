// ExcelActiveCell.jsx
(function() {

'use strict';

var React = require('react');

var ExcelActiveCell = React.createClass({

  render: function() {
    var style = {
      position: 'absolute',
      marginLeft: (this.props.cell.x * 20) + 'px',
      marginTop: (this.props.cell.y * 20) + 'px',
      zIndex: -3000,
      width: '20px',
      height: '20px',
      backgroundColor: '#D4E1F1'
    };
    return (
      <div data-x={this.props.cell.x} data-y={this.props.cell.y} style={style}>
      </div>
    );
  }

});

module.exports = ExcelActiveCell;

})();

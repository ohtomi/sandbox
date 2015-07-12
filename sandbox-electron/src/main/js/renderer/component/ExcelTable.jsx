// ExcelTable.jsx
(function() {

'use strict';

var React = require('react');
var ExcelCell = require('./ExcelCell.js');
var ExcelActiveCell = require('./ExcelActiveCell.js');

var ExcelTable = React.createClass({

  handleMouseClick: function(e) {
    var table = e.target;
    while (table.className !== 'jagrid') {
      table = table.parentNode;
    }

    var tableLeft = table.getBoundingClientRect().left;
    var tableTop = table.getBoundingClientRect().top;
    var cellLeft = e.clientX - tableLeft;
    var cellTop = e.clientY - tableTop;
    var cellX = Math.floor(cellLeft / 20) - 1;
    var cellY = Math.floor(cellTop / 20) - 1;
    this.props.handleMouseClick(cellX, cellY);
  },

  render: function() {
    var style = {
      width: '2000px',
      height: '2020px'
    };
    var rows = [];
    this.props.cells.forEach(function(cell, idx) {
      rows.push(<ExcelCell cell={cell} key={idx} />);
    });
    return (
      <div className="jagrid" style={style} onClick={this.handleMouseClick}>
        {rows}
        <ExcelActiveCell key="active-cell" cell={this.props.activeCell} />
      </div>
    );
  }

});

module.exports = ExcelTable;

})();

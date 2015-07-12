// ExcelView.jsx
(function() {

'use strict';

var React = require('react');
var ExcelToolbar = require('./ExcelToolbar.js');
var ExcelTable = require('./ExcelTable.js');
var misc = require('../misc.js');

var cells = [
  { x: 1, y: 2, label: misc.buildCellLabel(1, 2), func: null, value: 'We are using ...' },
  { x: 2, y: 5, label: misc.buildCellLabel(2, 5), func: '=B3', value: '=B3' }
];

function findCell(x, y) {
  for (var i = 0; i < cells.length; i++) {
    var cell = cells[i];
    if (cell.x === x && cell.y === y) {
      return cell;
    }
  }
  return null;
}

var ExcelView = React.createClass({

  getInitialState: function() {
    return {
      cells: cells,
      activeCell: {x: 0, y: 0, label: misc.buildCellLabel(0, 0), func: null, value: ''}
    };
  },

  handleMouseClick: function(x, y) {
    var cell = findCell(x, y);
    if (cell) {
      this.setState({activeCell: cell});
    } else {
      this.setState({activeCell: {x: x, y: y, label: misc.buildCellLabel(x, y), func: null, value: ''}});
    }
  },

  handleInput: function(value) {
    var cell = findCell(this.state.activeCell.x, this.state.activeCell.y);
    if (cell) {
      cell.value = value;
      this.setState({cells: cells});
    } else {
      cell = {
        x: this.state.activeCell.x,
        y: this.state.activeCell.y,
        label: misc.buildCellLabel(this.state.activeCell.x, this.state.activeCell.y),
        func: null,
        value: value
      };
      cells.push(cell);
      this.setState({cells: cells});
    }

    this.setState({
      activeCell: {
        x: this.state.activeCell.x,
        y: this.state.activeCell.y,
        label: misc.buildCellLabel(this.state.activeCell.x, this.state.activeCell.y),
        func: null,
        value: value
      }
    });
  },

  render: function() {
    return (
      <div>
        <ExcelToolbar activeCell={this.state.activeCell} handleInput={this.handleInput} />
        <ExcelTable cells={this.state.cells} handleMouseClick={this.handleMouseClick} />
      </div>
    );
  }

});

module.exports = ExcelView;

})();

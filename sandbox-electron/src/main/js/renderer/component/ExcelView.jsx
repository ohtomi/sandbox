// ExcelView.jsx
(function() {

'use strict';

var React = require('react');
var ExcelStore = require('../store/ExcelStore.js');
var ExcelToolbar = require('./ExcelToolbar.js');
var ExcelTable = require('./ExcelTable.js');
var misc = require('../misc.js');

var ExcelView = React.createClass({

  getInitialState: function() {
    return {
      cells: ExcelStore.getAllCells(),
      activeCell: {
        x: 0,
        y: 0,
        label: misc.buildCellLabel(0, 0),
        func: null,
        value: ''
      }
    };
  },

  handleMouseClick: function(x, y) {
    var cell = ExcelStore.getCell(x, y);
    if (cell) {
      this.setState({activeCell: cell});
    } else {
      this.setState({
        activeCell: {
          x: x,
          y: y,
          label: misc.buildCellLabel(x, y),
          func: null,
          value: ''
        }
      });
    }
  },

  handleInput: function(value) {
    var cell = ExcelStore.getCell(this.state.activeCell.x, this.state.activeCell.y);
    if (cell) {
      cell.value = value;
      cell.func = value.startsWith('=') ? misc.buildFormulaJsFunction(misc.parseExcelFunction(value.slice(1))) : null;
      ExcelStore.updateCell(cell);
      this.setState({cells: ExcelStore.getAllCells()});
    } else {
      cell = {
        x: this.state.activeCell.x,
        y: this.state.activeCell.y,
        label: misc.buildCellLabel(this.state.activeCell.x, this.state.activeCell.y),
        func: value.startsWith('=') ? misc.buildFormulaJsFunction(misc.parseExcelFunction(value.slice(1))) : null,
        value: value
      };
      ExcelStore.updateCell(cell);
      this.setState({cells: ExcelStore.getAllCells()});
    }

    this.setState({activeCell: cell});
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

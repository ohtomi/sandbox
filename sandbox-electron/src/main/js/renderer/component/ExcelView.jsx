// ExcelView.jsx
(function() {

'use strict';

var React = require('react');
var ExcelStore = require('../store/ExcelStore.js');
var ExcelToolbar = require('./ExcelToolbar.js');
var ExcelTable = require('./ExcelTable.js');
var misc = require('../misc.js');

var ExcelView = React.createClass({

  componentDidMount: function() {
    ExcelStore.addListener(this.handleExcelStoreEvent);
  },

  componentWillUnmount: function() {
    ExcelStore.removeListener(this.handleExcelStoreEvent);
  },

  getInitialState: function() {
    return {
      cells: ExcelStore.getAllCells(),
      activeCell: {
        x: 0,
        y: 0,
        label: misc.buildCellLabel(0, 0),
        func: null,
        value: ''
      },
      toolbar: {
        focus: false
      },
      viewMode: 'EXCEL' // 'EXCEL' or 'APPLICATION'
    };
  },

  handleExcelStoreEvent: function() {
    this.setState({cells: ExcelStore.getAllCells()});
  },

  handleMouseClick: function(x, y) {
    if (this.state.viewMode !== 'EXCEL') {
      return;
    }
    if (x < 0 || y < 0) {
      return;
    }

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
    this.setState({
      toolbar: {
        focus: (this.state.activeCell.x === x && this.state.activeCell.y === y)
      }
    });
  },

  handleInput: function(value) {
    var cell = ExcelStore.getCell(this.state.activeCell.x, this.state.activeCell.y);
    if (cell) {
      cell.value = value;
      cell.func = value.startsWith('=') ? misc.buildFormulaJsFunction(misc.parseExcelFunction(value.slice(1))) : null;
      ExcelStore.updateCell(cell);
    } else {
      cell = {
        x: this.state.activeCell.x,
        y: this.state.activeCell.y,
        label: misc.buildCellLabel(this.state.activeCell.x, this.state.activeCell.y),
        func: value.startsWith('=') ? misc.buildFormulaJsFunction(misc.parseExcelFunction(value.slice(1))) : null,
        value: value
      };
      ExcelStore.updateCell(cell);
    }

    this.setState({activeCell: cell});
  },

  changeViewMode: function(newMode) {
    if (newMode === 'EXCEL') {
      this.setState({
        activeCell: {
          x: 0,
          y: 0,
          label: misc.buildCellLabel(0, 0),
          func: null,
          value: ''
        }
      });
      this.setState({viewMode: newMode});
    } else if (newMode === 'APPLICATION') {
      this.setState({
        activeCell: {
          x: -1,
          y: -1,
          label: '---',
          func: null,
          value: ''
        }
      });
      this.setState({viewMode: newMode});
    }
  },

  render: function() {
    setTimeout(function() {
      if (!document.getElementsByClassName('jg-header') || document.getElementsByClassName('jg-header').length === 0) {
        jagrid.render();
      }
    }, 0);

    return (
      <div>
        <ExcelToolbar activeCell={this.state.activeCell} focus={this.state.toolbar.focus} viewMode={this.state.viewMode} handleInput={this.handleInput} changeViewMode={this.changeViewMode} />
        <ExcelTable cells={this.state.cells} activeCell={this.state.activeCell} handleMouseClick={this.handleMouseClick} />
      </div>
    );
  }

});

module.exports = ExcelView;

})();

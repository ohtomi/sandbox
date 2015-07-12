// ExcelStore.js
(function() {

'use strict';

var EventEmitter = require('events').EventEmitter;
var emitter = new EventEmitter();
var misc = require('../misc.js');

var cells = [
  { x: 1, y: 2, label: misc.buildCellLabel(1, 2), func: null, value: 'We are using' },
  { x: 2, y: 5, label: misc.buildCellLabel(2, 5),
    func: misc.buildFormulaJsFunction(misc.parseExcelFunction('if(true, "xxx", abs(3))')),
    value: '=if(true, "xxx", abs(3))' }
];

function addListener(listener) {
  emitter.on('ExcelStore', listener);
}

function removeListener(listener) {
  emitter.removeListener('ExcelStore', listener);
}

function getAllCells() {
  return cells.slice(0);
}

function getRealCell(x, y) {
  for (var i = 0; i < cells.length; i++) {
    var cell = cells[i];
    if (cell.x === x && cell.y === y) {
      return cell;
    }
  }
  return null;
}

function getCell(x, y) {
  var cell = getRealCell(x, y);
  if (cell) {
    return {
      x: cell.x,
      y: cell.y,
      label: cell.label,
      func: cell.func,
      value: cell.value
    };
  } else {
    return null;
  }
}

function updateCell(newCell) {
  var oldCell = getRealCell(newCell.x, newCell.y);
  if (oldCell) {
    oldCell.x = newCell.x;
    oldCell.y = newCell.y;
    oldCell.label = newCell.label;
    oldCell.func = newCell.func;
    oldCell.value = newCell.value;
  } else {
    oldCell = {
      x: newCell.x,
      y: newCell.y,
      label: newCell.label,
      func: newCell.func,
      value: newCell.value
    };
    cells.push(oldCell);
  }
  emitter.emit('ExcelStore');
}

module.exports = {
  addListener: addListener,
  removeListener: removeListener,
  getAllCells: getAllCells,
  getCell: getCell,
  updateCell: updateCell
};

})();

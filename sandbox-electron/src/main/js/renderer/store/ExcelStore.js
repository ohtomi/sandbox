// ExcelStore.js
(function() {

'use strict';

var EventEmitter = require('events').EventEmitter;
var emitter = new EventEmitter();
var misc = require('../misc.js');

var cells = [
  { x: 1, y: 1, label: misc.buildCellLabel(1, 1), func: null, value: 'Excel Viewer - user template' },
  { x: 20, y: 1, label: misc.buildCellLabel(20, 1),
    func: misc.buildFormulaJsFunction(misc.parseExcelFunction('if(true, "Excel関数(IF)", abs(3))')),
    value: '=if(true, "Excel関数(IF)", abs(3))' },
  { x: 2, y: 3, label: misc.buildCellLabel(2, 3), func: null, value: 'テキストボックス' },
  { x: 2, y: 4, label: misc.buildCellLabel(2, 4), func: null, value: 'ラジオボタン' },
  { x: 2, y: 5, label: misc.buildCellLabel(2, 5), func: null, value: 'チェックボックス' },
  { x: 2, y: 6, label: misc.buildCellLabel(2, 6), func: null, value: 'セレクトボックス' },
  { x: 2, y: 7, label: misc.buildCellLabel(2, 7), func: null, value: '（マルチ選択）' },
  { x: 10, y: 3, label: misc.buildCellLabel(10, 3),
    func: null,
    value: '{"type": "text", "name": "text-name", "default": "text-value"}'},
  { x: 10, y: 4, label: misc.buildCellLabel(10, 4),
    func: null,
    value: '{"type": "radio", "name": "radio-name", "value": ["radio-value1","radio-value2", "radio-value3"]}'},
  { x: 10, y: 5, label: misc.buildCellLabel(10, 5),
    func: null,
    value: '{"type": "checkbox", "name": "check-name", "value": ["check-value1", "check-value2"]}'},
  { x: 10, y: 6, label: misc.buildCellLabel(10, 6),
    func: null,
    value: '{"type": "select", "name": "select-name", "value": ["select-value1", "select-value2", "select-value3"]}'},
  { x: 10, y: 7, label: misc.buildCellLabel(10, 7),
    func: null,
    value: '{"type": "select", "name": "select-m-name", "multiple": true, "size": 5, "value": ["select-value1", "select-value2", "select-value3"]}'}
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

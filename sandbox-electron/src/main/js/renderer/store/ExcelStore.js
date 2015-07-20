// ExcelStore.js
(function() {

'use strict';

var EventEmitter = require('events').EventEmitter;
var emitter = new EventEmitter();
var Excel = require('remote').require('exceljs');
var misc = require('../misc.js');

var cells = [];

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

function readFile(excelFile) {
  var workbook = new Excel.Workbook();
  workbook.xlsx.readFile(excelFile)
    .then(function() {
      cells = [];
      workbook.eachSheet(function(sheet) {
        sheet.eachRow(function(row) {
          row.eachCell(function(cell) {
            var position = misc.parseCellLabel(cell.address);
            if (cell.type !== 6) {
              cells.push({
                x: position.x,
                y: position.y,
                label: cell.address,
                func: null,
                value: cell.value
              });
            } else {
              cells.push({
                x: position.x,
                y: position.y,
                label: cell.address,
                func: misc.buildFormulaJsFunction(misc.parseExcelFunction(cell.value.formula)),
                value: '=' + cell.value.formula
              });
            }
          });
        });
      });
      emitter.emit('ExcelStore');
    })
    .catch(function(err) {
      console.log(err);
    });
}

module.exports = {
  addListener: addListener,
  removeListener: removeListener,
  getAllCells: getAllCells,
  getCell: getCell,
  updateCell: updateCell,
  readFile: readFile
};

})();

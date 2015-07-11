// main.js
(function() {

'use strict';

var app = require('app');
var BrowserWindow = require('browser-window');

require('crash-reporter').start();

var mainWindow = null;

app.on('window-all-closed', function() {
  if (process.platform !== 'darwin') {
    app.quit();
  }
});

app.on('ready', function() {
  mainWindow = new BrowserWindow({width: 800, height: 1000});
  mainWindow.loadUrl('file://' + __dirname + '/../html/index.html');
  mainWindow.openDevTools();
  mainWindow.on('closed', function() {
    mainWindow = null;
  });
  mainWindow.webContents.on('will-navigate', function(e) {
    e.preventDefault();
  });
});

})();

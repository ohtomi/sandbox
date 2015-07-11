// ExcelView.jsx
(function() {

'use strict';

var React = require('react');
var ExcelCell = require('./ExcelCell.js');

var ExcelView = React.createClass({

  handleMouseClick: function(e) {
    console.log('click', e, e.target, e.relatedTarget, e.clientX, e.clientY, e.pageX, e.pageY, e.screenX, e.screenY);
  },

  render: function() {
    var style = {
      width: '2000px',
      height: '100px'
    };
    var rows = [];
    this.props.cells.forEach(function(cell, idx) {
      rows.push(<ExcelCell cell={cell} key={idx} />);
    });
    return (
      <div className="jagrid" style={style} onClick={this.handleMouseClick}>
        {rows}
      </div>
    );
  }

});

module.exports = ExcelView;

})();

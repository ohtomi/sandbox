// ExcelToolbar.jsx
(function() {

'use strict';

var React = require('react');

var ExcelToolbar = React.createClass({

  handleInput: function(e) {
    this.props.handleInput(e.target.value);
  },

  render: function() {
    return (
      <div style={{width: '100%', marginTop: '2px', marginBottom: '2px'}}>
        <input type="text" style={{width: '10%'}} value={this.props.activeCell.label} readOnly />
        {' fx'}
        <input type="text" style={{width: '80%'}} value={this.props.activeCell.value} onChange={this.handleInput} />
      </div>
    );
  }

});

module.exports = ExcelToolbar;

})();

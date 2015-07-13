// ExcelToolbar.jsx
(function() {

'use strict';

var React = require('react');

var ExcelToolbar = React.createClass({

  handleInput: function(e) {
    this.props.handleInput(e.target.value);
  },

  render: function() {
    var this_ = this;
    setTimeout(function() {
      if (this_.props.focus) {
        React.findDOMNode(this_.refs.value).focus();
      }
    }, 0);

    return (
      <div style={{width: '100%', marginTop: '2px', marginBottom: '2px'}}>
        <input type="text" style={{width: '10%'}} ref="label" value={this.props.activeCell.label} readOnly />
        <span style={{marginLeft: '10px', marginRight: '5px'}}>{'fx'}</span>
        <input type="text" style={{width: '80%'}} ref="value" value={this.props.activeCell.value} onChange={this.handleInput} />
      </div>
    );
  }

});

module.exports = ExcelToolbar;

})();

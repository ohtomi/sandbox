// ExcelToolbar.jsx
(function() {

'use strict';

var React = require('react');

var ExcelToolbar = React.createClass({

  handleInput: function(e) {
    this.props.handleInput(e.target.value);
  },

  changeViewMode: function(e) {
    var newMode = e.target.value;
    this.props.changeViewMode(newMode);
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
        <div>
          <label style={{marginRight: '10px'}}>
            <input type="radio" name="viewMode" value="EXCEL" checked={this.props.viewMode === 'EXCEL' ? 'checked' : ''} onChange={this.changeViewMode}>Excel</input>
          </label>
          <label style={{marginRight: '10px'}}>
            <input type="radio" name="viewMode" value="APPLICATION" checked={this.props.viewMode === 'APPLICATION' ? 'checked' : ''} onChange={this.changeViewMode}>Application</input>
          </label>
        </div>
        <div>
          <input type="text" style={{width: '10%'}} ref="label" value={this.props.activeCell.label} readOnly />
          <span style={{marginLeft: '10px', marginRight: '5px'}}>{'fx'}</span>
          <input type="text" style={{width: '80%'}} ref="value" value={this.props.activeCell.value} onChange={this.handleInput} />
        </div>
      </div>
    );
  }

});

module.exports = ExcelToolbar;

})();

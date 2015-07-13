// HtmlForm.jsx
(function() {

'use strict';

var React = require('react');

var HtmlForm = React.createClass({

  handleInput: function(e) {
  },

  renderTextBox: function(json) {
    return (
      <input type="text" style={{zIndex: 3000}} name={json.name} defaultValue={json.default} />
    );
  },

  renderRadioButton: function(json) {
    return (
      <input type="radio" style={{zIndex: 3000}} name={json.name} value={json.value}>
        {json.name}
      </input>
    );
  },

  renderCheckBox: function(json) {
    return (
      <input type="checkbox" style={{zIndex: 3000}} name={json.name} value={json.value}>
        {json.name}
      </input>
    );
  },

  render: function() {
    try {
      var json = JSON.parse(this.props.value);

      if (json.type === 'text') {
        return this.renderTextBox(json);
      } else if (json.type === 'radio') {
          return this.renderRadioButton(json);
      } else if (json.type === 'checkbox') {
        return this.renderCheckBox(json);
      } else {
        return <span>{this.props.value}</span>;
      }

    } catch (e) {
      return <span>{this.props.value}</span>;
    }
  }

});

module.exports = HtmlForm;

})();

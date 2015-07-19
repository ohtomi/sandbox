// HtmlForm.jsx
(function() {

'use strict';

var React = require('react');

var HtmlForm = React.createClass({

  handleInput: function(e) {
  },

  renderTextBox: function(json) {
    return (
      <div style={{zIndex: 3000}}>
        <input type="text" name={json.name} defaultValue={json.default} />
      </div>
    );
  },

  renderRadioButton: function(json) {
    var rows = [];
    json.value.forEach(function(valueItem) {
      rows.push(
        <label style={{marginRight: '10px'}} key={valueItem}>
          <input type="radio" name={json.name} value={valueItem}>
            {valueItem}
          </input>
        </label>
      );
    });
    return (
      <div style={{zIndex: 3000}}>
        {rows}
      </div>
    );
  },

  renderCheckBox: function(json) {
    var rows = [];
    json.value.forEach(function(valueItem) {
      rows.push(
        <label style={{marginRight: '10px'}} key={valueItem}>
          <input type="checkbox" name={json.name} value={valueItem}>
            {valueItem}
          </input>
        </label>
      );
    });
    return (
      <div style={{zIndex: 3000}}>
        {rows}
      </div>
    );
  },

  renderSelectBox: function(json) {
    var rows = [];
    json.value.forEach(function(valueItem) {
      rows.push(<option value={valueItem} key={valueItem}>{valueItem}</option>);
    });
    return (
      <div style={{marginTop: json.multiple ? '5px' : '0px', zIndex: 3000}}>
        <select name={json.name}
            multiple={json.multiple} size={json.size ? Number(json.size) : 1}>
          {rows}
        </select>
      </div>
    );
  },

  renderButton: function(json) {
    return (
      <div style={{zIndex: 3000}}>
        <button name={json.name} value={json.value}>
          {json.value}
        </button>
      </div>
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
      } else if (json.type === 'select') {
        return this.renderSelectBox(json);
      } else if (json.type === 'button') {
        return this.renderButton(json);
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

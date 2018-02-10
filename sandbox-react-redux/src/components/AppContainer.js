import App from '../components/App';

import { connect, } from 'react-redux';
import { bindActionCreators } from 'redux';

import * as count from '../count/index';
import * as calc from '../calc/index';

const mapStateToProps = (state) => {
  return { state };
};

const mapDispatchToProps = (dispatch) => {
  return {
    actions: {
      count: { ...bindActionCreators(count, dispatch) },
      calc: { ...bindActionCreators(calc, dispatch) }
    }
  };
};

const AppContainer = connect(mapStateToProps, mapDispatchToProps)(App);

export default AppContainer;

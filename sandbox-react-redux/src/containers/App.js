import App from '../components/App';

import { connect, } from 'react-redux';
import { bindActionCreators } from 'redux';

import * as count from '../actions/count';

const mapStateToProps = (state) => {
  return { state };
};

const mapDispatchToProps = (dispatch) => {
  return {
    actions: {
      count: { ...bindActionCreators(count, dispatch) }
    }
  };
};

const AppContainer = connect(mapStateToProps, mapDispatchToProps)(App);

export default AppContainer;

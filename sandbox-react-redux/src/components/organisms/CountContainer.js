import Count from '../molecules/Count'

import { compose, pure } from 'recompose'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'

import * as count from '../../count/index'

const mapStateToProps = (state) => {
    return state.count
}

const mapDispatchToProps = (dispatch) => {
    return bindActionCreators(count, dispatch)
}

const enhance = compose(
    connect(mapStateToProps, mapDispatchToProps),
    pure
)

const CountContainer = enhance(Count)

export default CountContainer

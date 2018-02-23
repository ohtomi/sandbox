import Calc from '../molecules/Calc'

import { compose, pure } from 'recompose'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'

import * as calc from '../../calc/index'

const mapStateToProps = (state) => {
    return state.calc
}

const mapDispatchToProps = (dispatch) => {
    return bindActionCreators(calc, dispatch)
}

const enhance = compose(
    connect(mapStateToProps, mapDispatchToProps),
    pure
)

const CalcContainer = enhance(Calc)

export default CalcContainer

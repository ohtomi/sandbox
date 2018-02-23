import Header from '../molecules/Header'

import { compose, pure } from 'recompose'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'

import * as misc from '../../misc/index'

const mapStateToProps = (state) => {
    return state.misc
}

const mapDispatchToProps = (dispatch) => {
    return bindActionCreators(misc, dispatch)
}

const enhance = compose(
    connect(mapStateToProps, mapDispatchToProps),
    pure
)

const HeaderContainer = enhance(Header)

export default HeaderContainer

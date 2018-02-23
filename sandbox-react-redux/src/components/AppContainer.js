import App from './App'

import { compose, pure, lifecycle } from 'recompose'
import { connect } from 'react-redux'
import { bindActionCreators } from 'redux'

import * as misc from '../misc/index'
import * as routing from '../routing/index'

const mapStateToProps = (state, props) => {
    const { history, route } = props
    return {
        state,
        history,
        route
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
        actions: {
            misc: bindActionCreators(misc, dispatch),
            routing: bindActionCreators(routing, dispatch)
        }
    }
}

const enhance = compose(
    connect(mapStateToProps, mapDispatchToProps),
    pure,
    lifecycle({
        componentDidMount() {
            const { history, actions: { routing } } = this.props
            history.listen((location) => routing.changeLocation(location))
        }
    })
)

const AppContainer = enhance(App)

export default AppContainer

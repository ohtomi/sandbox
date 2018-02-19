import React from 'react'
import Link from './Link'

const PageFoo = ({ state, actions }) => {
    return (
        <React.Fragment>
            [ {state.routing.pathname} ]
            [
            <Link to="/" {...{ state, actions }}>
                go to root
            </Link>
            ]
            [
            <Link to="/bar" {...{ state, actions }}>
                go to bar
            </Link>
            ]
        </React.Fragment>
    )
}

export default PageFoo

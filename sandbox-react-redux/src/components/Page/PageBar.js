import React from 'react'
import Link from './Link'

const PageBar = ({ state, actions }) => {
    return (
        <React.Fragment>
            [ {state.routing.pathname} ]
            [
            <Link to="/" {...{ state, actions }}>
                go to root
            </Link>
            ]
            [
            <Link to="/foo/12345" {...{ state, actions }}>
                go to foo
            </Link>
            ]
        </React.Fragment>
    )
}

export default PageBar

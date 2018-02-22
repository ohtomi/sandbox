import React from 'react'
import Link from '../atoms/Link'

const PageRoot = ({ state, actions }) => {
    return (
        <React.Fragment>
            [ {state.routing.pathname} ]
            [
            <Link to="/foo/12345" {...{ state, actions }}>
                go to foo
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

export default PageRoot

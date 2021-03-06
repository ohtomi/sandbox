import React from 'react'
import HeaderContainer from '../organisms/HeaderContainer'
import CountContainer from '../organisms/CountContainer'
import CalcContainer from '../organisms/CalcContainer'
import Link from '../atoms/Link'

const PageRoot = ({ state: { misc: { locked }, routing: { pathname } }, actions }) => {
    return (
        <div className={locked ? 'App App-locked' : 'App'}>
            <HeaderContainer />
            <p className="App-intro">
                <CountContainer />
            </p>
            <p className="App-intro">
                <CalcContainer />
            </p>
            <p className="App-intro">
                [ {pathname} ]
                [ <Link to="/foo/12345" {...actions.routing}>go to foo</Link> ]
                [ <Link to="/bar" {...actions.routing}>go to bar</Link> ]
            </p>
        </div >
    )
}

export default PageRoot

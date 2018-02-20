import React from 'react'
import Header from './Layout/Header'
import Content from './Layout/Content'
import Count from './Layout/Count'
import Calc from './Layout/Calc'

const Layout = ({ state, actions, route: { Component } }) => {
    return (
        <React.Fragment>
            <Header {...{ state, actions }} />
            <Content>
                <Count {...{ state, actions }} />
            </Content>
            <Content>
                <Calc {...{ state, actions }} />
            </Content>
            <Content>
                <Component {...{ state, actions }} />
            </Content>
        </React.Fragment>
    )
}

export default Layout

/*
* This file demonstrates a basic ReactXP app.
*/

import EventEmitter = require('events');
import RX = require('reactxp');

import MainPanel = require('./MainPanel');
import SecondPanel = require('./SecondPanel');

interface AppProps {
    emitter: EventEmitter;
}

enum NavigationRouteId {
    MainPanel,
    SecondPanel
}

const styles = {
    navCardStyle: RX.Styles.createViewStyle({
        backgroundColor: '#f5fcff'
    })
};

class App extends RX.Component<AppProps, null> {
    private _navigator: RX.Navigator;

    componentDidMount() {
        this._navigator.immediatelyResetRouteStack([{
            routeId: NavigationRouteId.MainPanel,
            sceneConfigType: RX.Types.NavigatorSceneConfigType.Fade
        }]);
    }

    render() {
        return (
            <RX.Navigator
                ref={ this._onNavigatorRef }
                renderScene={ this._renderScene }
                cardStyle={ styles.navCardStyle }
            />
        );
    }

    private _onNavigatorRef = (navigator: RX.Navigator) => {
        this._navigator = navigator;
    }

    private _renderScene = (navigatorRoute: RX.Types.NavigatorRoute) => {
        switch (navigatorRoute.routeId) {
            case NavigationRouteId.MainPanel:
                return <MainPanel emitter={ this.props.emitter } onPressNavigate={ this._onPressNavigate } />

            case NavigationRouteId.SecondPanel:
                return <SecondPanel onNavigateBack={ this._onPressBack } />
        }

        return null;
    }

    private _onPressNavigate = () => {
        this._navigator.push({
            routeId: NavigationRouteId.SecondPanel,
            sceneConfigType: RX.Types.NavigatorSceneConfigType.FloatFromRight,
            customSceneConfig: {
                hideShadow: true
            }
        });
    }

    private _onPressBack = () => {
        this._navigator.pop();
    }
}

export = App;

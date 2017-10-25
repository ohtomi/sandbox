/*
* This file demonstrates a basic ReactXP app.
*/

import EventEmitter = require('events');
import RX = require('reactxp');

interface MainPanelProps {
    emitter: EventEmitter;
    onPressNavigate: () => void;
}

interface MainPanelState {
    listItems: { open: boolean, date: string, text: string }[];
}

const styles = {
    scroll: RX.Styles.createScrollViewStyle({
        alignSelf: 'stretch',
        backgroundColor: '#f5fcff'
    }),
    container: RX.Styles.createViewStyle({
        padding: 16,
        justifyContent: 'flex-start',
        alignItems: 'flex-start'
    })
};

class MainPanel extends RX.Component<MainPanelProps, MainPanelState> {

    constructor() {
        super();

        this.state = {
            listItems: []
        };
    }

    componentDidMount() {
        this.props.emitter.on('from-devtools', this._onMessageFromDevTools);
    }

    render() {
        const items = this.state.listItems.map((listItem, index) => {
            if (listItem.open) {
                return (
                    <RX.Text key={index} onPress={this._onPressClickableText.bind(this, index)}>
                        {'\u25be '}
                        <RX.Text>{listItem.date}</RX.Text>
                        <br />
                        {'  '}
                        <RX.Text>{listItem.text}</RX.Text>
                    </RX.Text>
                );
            } else {
                return (
                    <RX.Text key={index} onPress={this._onPressClickableText.bind(this, index)}>
                        {'\u25b8 '}
                        <RX.Text>{listItem.date}</RX.Text>
                    </RX.Text>
                );
            }
        });

        return (
            <RX.ScrollView style={styles.scroll}>
                <RX.View style={styles.container}>
                    {items}
                </RX.View>
            </RX.ScrollView>
        );
    }

    private _onMessageFromDevTools = (message: any) => {
        const newState = this.state;
        newState.listItems.push({
            open: false,
            date: new Date().toUTCString(),
            text: JSON.stringify(message)
        });
        this.setState(newState);
    }

    private _onPressClickableText = (index: number, e: RX.Types.SyntheticEvent) => {
        const newState = this.state;
        newState.listItems[index].open = !newState.listItems[index].open;
        this.setState(newState);
    }
}

export = MainPanel;

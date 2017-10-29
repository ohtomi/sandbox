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
    container: RX.Styles.createViewStyle({
        alignSelf: 'stretch',
        backgroundColor: '#f5fcff',
        flex: 1,
        flexDirection: 'row'
    }),
    container1: RX.Styles.createViewStyle({
        padding: 16,
        width: 300
    }),
    form: RX.Styles.createViewStyle({
        padding: 8,
        maxHeight: 30,
        backgroundColor: '#efefef',
        flex: 1,
        flexDirection: 'row'
    }),
    formLabel: RX.Styles.createViewStyle({
        width: 80
    }),
    container2: RX.Styles.createScrollViewStyle({
        padding: 16
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
                    <p>
                        <RX.Text key={index} onPress={this._onPressClickableText.bind(this, index)}>
                            {'\u25be '}
                            <RX.Text>{listItem.date}</RX.Text>
                            <br />
                            {'  '}
                            <RX.Text>{listItem.text}</RX.Text>
                        </RX.Text>
                    </p>
                );
            } else {
                return (
                    <p>
                        <RX.Text key={index} onPress={this._onPressClickableText.bind(this, index)}>
                            {'\u25b8 '}
                            <RX.Text>{listItem.date}</RX.Text>
                        </RX.Text>
                    </p>
                );
            }
        });

        return (
            <RX.View style={styles.container}>
                <RX.View style={styles.container1}>
                    <RX.Button onPress={this._onPressRequestButton.bind(this)}>
                        {'Send Request'}
                    </RX.Button>
                    <RX.View style={styles.form}>
                        <div style={styles.formLabel}>{'ID: '}</div>
                        <div><RX.TextInput /></div>
                    </RX.View>
                    <RX.View style={styles.form}>
                        <div style={styles.formLabel}>{'Password: '}</div>
                        <RX.TextInput />
                    </RX.View>
                </RX.View>
                <RX.ScrollView style={styles.container2}>
                    {items}
                </RX.View>
            </RX.View>
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

    private _onPressRequestButton = () => {
        alert('xxxx');
        console.log('xxxx');

        fetch('https://microsoft.github.io/reactxp/docs/components/button.html')
            .then(res => res.text())
            .then(text => {
                const newState = this.state;
                newState.listItems.push({
                    open: false,
                    date: new Date().toUTCString(),
                    text: text.substring(0, 100)
                });
                this.setState(newState);
            });
    }

    private _onPressClickableText = (index: number, e: RX.Types.SyntheticEvent) => {
        const newState = this.state;
        newState.listItems[index].open = !newState.listItems[index].open;
        this.setState(newState);
    }
}

export = MainPanel;

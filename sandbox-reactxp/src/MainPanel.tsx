/*
* This file demonstrates a basic ReactXP app.
*/

import RX = require('reactxp');

interface MainPanelProps {
    onPressNavigate: () => void;
}

interface MainPanelState {
    markState: boolean;
    inputValue: string;
    selectedValue: string;
    listItems: RX.Types.PickerPropsItem[];
}

const styles = {
    scroll: RX.Styles.createScrollViewStyle({
        alignSelf: 'stretch',
        backgroundColor: '#f5fcff'
    }),
    container: RX.Styles.createViewStyle({
        padding: 16,
        justifyContent: 'center',
        alignItems: 'center'
    }),
    container2: RX.Styles.createViewStyle({
        padding: 16,
        justifyContent: 'flex-start',
        alignItems: 'flex-start'
    }),
    helloWorld: RX.Styles.createTextStyle({
        fontSize: 48,
        fontWeight: 'bold',
        marginBottom: 28
    }),
    welcome: RX.Styles.createTextStyle({
        fontSize: 32,
        marginBottom: 12
    }),
    instructions: RX.Styles.createTextStyle({
        fontSize: 16,
        color: '#aaa',
        marginBottom: 16
    }),
    docLink: RX.Styles.createLinkStyle({
        fontSize: 16,
        color: 'blue',
        marginBottom: 16
    }),
    inputBox: RX.Styles.createTextInputStyle({
        width: 400,
        marginTop: 8,
        marginBottom: 8,
        padding: 4
    }),
    picker: RX.Styles.createPickerStyle({
        width: 400,
        marginTop: 8,
        marginBottom: 8
    }),
    roundButton: RX.Styles.createViewStyle({
        margin: 16,
        borderRadius: 16,
        backgroundColor: '#7d88a9'
    }),
    buttonText: RX.Styles.createTextStyle({
        fontSize: 16,
        marginVertical: 6,
        marginHorizontal: 12,
        color: 'white'
    })
};

const marks = {
    closed: {
        head: '\u25b8',
        body: 'ほげ ふが ...'
    },
    opened: {
        head: '\u25be',
        body: 'ほげ ふが！！！\nふー ばー ばず！！！'
    }
};

class MainPanel extends RX.Component<MainPanelProps, MainPanelState> {
    private _translationValue: RX.Animated.Value;
    private _animatedStyle: RX.Types.AnimatedTextStyleRuleSet;

    constructor() {
        super();

        this.state = {
            markState: true,
            inputValue: '',
            selectedValue: 'value-2',
            listItems: [
                { label: 'label-1', value: 'value-1' },
                { label: 'label-2', value: 'value-2' },
                { label: 'label-3', value: 'value-3' },
                { label: 'label-4', value: 'value-4' },
                { label: 'label-5', value: 'value-5' }
            ]
        };

        this._translationValue = new RX.Animated.Value(-100);
        this._animatedStyle = RX.Styles.createAnimatedTextStyle({
            transform: [
                {
                    translateY: this._translationValue
                }
            ]
        });
    }

    componentDidMount() {
        let animation = RX.Animated.timing(this._translationValue, {
            toValue: 0,
            easing: RX.Animated.Easing.OutBack(),
            duration: 500
        }
        );

        animation.start();
    }

    render() {
        return (
            <RX.ScrollView style={styles.scroll}>
                <RX.View style={styles.container}>
                    <RX.Animated.Text style={[styles.helloWorld, this._animatedStyle]}>
                        Hello World
                    </RX.Animated.Text>
                    <RX.Text style={styles.welcome}>
                        Welcome to ReactXP
                    </RX.Text>
                    <RX.Text style={styles.instructions}>
                        Edit App.tsx to get started
                    </RX.Text>
                    <RX.Link style={styles.docLink} url={'https://microsoft.github.io/reactxp/docs'}>
                        View ReactXP documentation
                    </RX.Link>

                    <RX.TextInput style={styles.inputBox}
                        autoFocus={true} placeholder={'何かを入力すべし'}
                        value={this.state.inputValue} onChangeText={this._onChangeText} />
                    <RX.Picker style={styles.picker}
                        items={this.state.listItems}
                        selectedValue={this.state.selectedValue} onValueChange={this._onValueChange} />

                    <RX.ActivityIndicator color={'black'} size={'tiny'} />
                    <RX.Button style={styles.roundButton} onPress={this._onPressNavigate}>
                        <RX.Text style={styles.buttonText}>
                            See More Examples
                        </RX.Text>
                    </RX.Button>

                    <RX.Button style={styles.roundButton} onPress={this._onPressAlert}>
                        <RX.Text style={styles.buttonText}>
                            Open Alert
                        </RX.Text>
                    </RX.Button>
                </RX.View>
                <hr/>
                <RX.View style={styles.container2}>
                    <RX.Text onPress={this._onPressClickableText}>
                        {this.state.markState ? marks.closed.head : marks.opened.head}
                        <RX.Text> {this.state.markState ? marks.closed.body : marks.opened.body}</RX.Text>
                    </RX.Text>
                </RX.View>
            </RX.ScrollView>
        );
    }

    private _onPressClickableText = (e: RX.Types.SyntheticEvent) => {
        const newState = this.state;
        newState.markState = !newState.markState;
        this.setState(newState);
    }

    private _onChangeText = (newValue: string) => {
        const newState = this.state;
        newState.inputValue = newValue;
        this.setState(newState);
    }

    private _onValueChange = (itemValue: string, itemPosition: number) => {
        const newState = this.state;
        newState.selectedValue = itemValue;
        this.setState(newState);
    }

    private _onPressNavigate = () => {
        this.props.onPressNavigate();
    }

    private _onPressAlert = () => {
        RX.Alert.show('警告', `${new Date()} ダイアログメッセージ ダイアログメッセージ`);
    }
}

export = MainPanel;

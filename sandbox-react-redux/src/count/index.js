import * as ActionType from '../actions';

const initialState = {
    value: 0
};

export default function reducer(state = initialState, action) {
    switch (action.type) {
        case ActionType.INCREMENT:
            return Object.assign({}, state, {
                value: state.value + 1
            });
        case ActionType.DECREMENT:
            return Object.assign({}, state, {
                value: state.value - 1
            });
        default:
            return state;
    }
}

export function incrementAsync() {
    return (dispatch) => {
        setTimeout(() => {
            dispatch(increment());
        }, 1000);
    }
}

export function increment() {
    return {
        type: ActionType.INCREMENT
    };
}

export function decrement() {
    return {
        type: ActionType.DECREMENT
    };
}

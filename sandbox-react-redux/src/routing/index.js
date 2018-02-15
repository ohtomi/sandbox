import * as ActionType from '../actions';

const initialState = {
    pathname: '/',
    search: '',
    hash: ''
};

export default function reducer(state = initialState, action) {
    switch (action.type) {
        case ActionType.CHANGE_LOCATION:
            return Object.assign({}, state, {
                ...action.payload
            });
        default:
            return state;
    }
}

export function pushHistory(href) {
    return {
        type: ActionType.PUSH_HISTORY,
        payload: { href }
    };
}

export function replaceHistory(href) {
    return {
        type: ActionType.REPLACE_HISTORY,
        payload: { href }
    };
}

export function goTo(index) {
    return {
        type: ActionType.GO_TO,
        payload: { index }
    };
}

export function goBack() {
    return {
        type: ActionType.GO_BACK
    };
}

export function goForward() {
    return {
        type: ActionType.GO_FORWARD
    };
}

export function changeLocation({ pathname, search, hash }) {
    return {
        type: ActionType.CHANGE_LOCATION,
        payload: { pathname, search, hash }
    };
}

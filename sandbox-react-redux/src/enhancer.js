export default function enhancer() {
    return (next) => (reducer, preloadedState) => {
        const initialState = Object.assign({}, preloadedState, loadState(restoreState));
        const store = next(reducer, initialState);
        store.subscribe(() => saveState(store.getState()));
        return store;
    }
}

const restoreState = (state) => {
    return {
        misc: state
    };
}

const loadState = (transform) => {
    const state = {
        locked: true
    };
    return transform(state);
}

const saveState = (state) => {
    if (state.misc) {
        console.log(state.misc);
    }
}

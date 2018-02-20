export default () => {
    return (next) => (reducer, preloadedState) => {
        const initialState = Object.assign({}, preloadedState, loadState(restoreState))
        const store = next(reducer, initialState)
        store.subscribe(() => saveState(store.getState(), filterState))
        return store
    }
}

const restoreState = (state) => {
    return {
        misc: state
    }
}

const filterState = (state) => {
    return state.misc
}

const storageKey = 'sample'

const loadState = (transform) => {
    const saved = window.localStorage.getItem(storageKey)
    if (saved) {
        return transform(JSON.parse(saved))
    }
}

const saveState = (state, transform) => {
    const saving = transform(state)
    if (saving) {
        window.localStorage.setItem(storageKey, JSON.stringify(saving))
    }
}

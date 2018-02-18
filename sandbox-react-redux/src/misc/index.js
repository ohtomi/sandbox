import * as ActionType from '../actions'

const initialState = {
    locked: false
}

export default function reducer(state = initialState, action) {
    switch (action.type) {
        case ActionType.LOCK_UI:
            return Object.assign({}, state, {
                locked: true
            })
        case ActionType.UNLOCK_UI:
            return Object.assign({}, state, {
                locked: false
            })
        default:
            return state
    }
}

export function lockUi() {
    return {
        type: ActionType.LOCK_UI
    }
}

export function unlockUi() {
    return {
        type: ActionType.UNLOCK_UI
    }
}

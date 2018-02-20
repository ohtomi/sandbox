import * as ActionType from '../actions'

const initialState = {
    locked: false
}

export default (state = initialState, action) => {
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

export const lockUi = () => {
    return {
        type: ActionType.LOCK_UI
    }
}

export const unlockUi = () => {
    return {
        type: ActionType.UNLOCK_UI
    }
}

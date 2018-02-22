import * as ActionType from '../actions'

const initialState = {
    value: 0
}

export default (state = initialState, action) => {
    switch (action.type) {
        case ActionType.INCREMENT:
            return Object.assign({}, state, {
                value: state.value + 1
            })
        case ActionType.DECREMENT:
            return Object.assign({}, state, {
                value: state.value - 1
            })
        default:
            return state
    }
}

export const incrementAsync = () => (dispatch) => {
    setTimeout(() => {
        dispatch(increment())
    }, 1000)
}

export const increment = () => {
    return {
        type: ActionType.INCREMENT
    }
}

export const decrement = () => {
    return {
        type: ActionType.DECREMENT
    }
}

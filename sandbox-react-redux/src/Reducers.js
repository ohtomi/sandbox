import { ActionType } from './Actions';

function reducer(state = { value: 0 }, action) {
    switch (action.type) {
        case ActionType.INCREMENT:
            return { value: state.value + 1 };
        case ActionType.DECREMENT:
            return { value: state.value - 1 };
        default:
            return state;
    }
}

export default reducer;

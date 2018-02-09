import { ActionType } from '../actions/count';

const initialState = {
    value: 0
};

function count(state = initialState, action) {
    switch (action.type) {
        case ActionType.INCREMENT:
            return { value: state.value + 1 };
        case ActionType.DECREMENT:
            return { value: state.value - 1 };
        default:
            return state;
    }
}

export default count;

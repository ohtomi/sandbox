import { ActionType, Op } from '../actions/calc';

const initialState = {
    op: Op.ADD,
    answer: 10,
    arg1: 7,
    arg2: 3
};

const answer = (op, arg1, arg2) => {
    switch (op) {
        case Op.ADD:
            return arg1 + arg2;
        case Op.SUBTRACT:
            return arg1 - arg2;
        default:
            return 0;
    }
};

function calc(state = initialState, action) {
    switch (action.type) {
        case ActionType.SELECT_OP:
            return Object.assign({}, state, {
                op: action.payload.op,
                answer: answer(action.payload.op, state.arg1, state.arg2)
            });
        case ActionType.UPDATE1:
            return Object.assign({}, state, {
                arg1: +action.payload.value,
                answer: answer(state.op, +action.payload.value, state.arg2)
            });
        case ActionType.UPDATE2:
            return Object.assign({}, state, {
                arg2: +action.payload.value,
                answer: answer(state.op, state.arg1, +action.payload.value)
            });
        default:
            return state;
    }
}

export default calc;

export const ActionType = {
    SELECT_OP: 'SELECT_OP',
    UPDATE1: 'UPDATE1',
    UPDATE2: 'UPDATE2'
};

export const Op = {
    ADD: 'ADD',
    SUBTRACT: 'SUBTRACT'
};

export function selectOp(op) {
    return {
        type: ActionType.SELECT_OP,
        payload: { op }
    };
}

export function update1(value) {
    return {
        type: ActionType.UPDATE1,
        payload: { value }
    }
}

export function update2(value) {
    return {
        type: ActionType.UPDATE2,
        payload: { value }
    }
}

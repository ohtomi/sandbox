export const ActionType = {
    INCREMENT: 'INCREMENT',
    DECREMENT: 'DECREMENT'
};

export function incrementAsync() {
    return (dispatch) => {
        setTimeout(() => {
            dispatch(increment());
        }, 1000);
    }
}

export function increment() {
    return {
        type: ActionType.INCREMENT
    };
}

export function decrement() {
    return {
        type: ActionType.DECREMENT
    };
}

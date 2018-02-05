export const ActionType = {
    INCREMENT: 'INCREMENT',
    DECREMENT: 'DECREMENT'
};

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

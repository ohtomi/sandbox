import * as ActionType from './actions'

export default (history) => () => (next) => (action) => {
    switch (action.type) {
        case ActionType.PUSH_HISTORY:
            history.push(action.payload.href)
            break
        case ActionType.REPLACE_HISTORY:
            history.replace(action.payload.href)
            break
        case ActionType.GO_TO:
            history.go(action.payload.index)
            break
        case ActionType.GO_BACK:
            history.goBack()
            break
        case ActionType.GO_FORWARD:
            history.goForward()
            break
        default:
            return next(action)
    }
}

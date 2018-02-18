import React from 'react'

const Count = ({ state, actions }) => {
    return (
        <React.Fragment>
            current value: {state.count.value} <br />
            <button onClick={() => { actions.count.incrementAsync() }}>increment value</button> <br />
            <button onClick={() => { actions.count.decrement() }}>decrement value</button>
        </React.Fragment>
    )
}

export default Count

import React from 'react'

const Count = ({ value, incrementAsync, decrement }) => {
    return (
        <React.Fragment>
            current value: {value} <br />
            <button onClick={() => { incrementAsync() }}>increment value</button> <br />
            <button onClick={() => { decrement() }}>decrement value</button>
        </React.Fragment>
    )
}

export default Count

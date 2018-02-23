import React from 'react'

import { Op } from '../../calc/constants'

const Calc = ({ answer, arg1, arg2, op, update1, update2, selectOp }) => {
    return (
        <React.Fragment>
            <input type="number" readOnly value={answer} />
            <span className="App-margin">=</span>
            <input type="number" defaultValue={arg1} onChange={(ev) => { update1(ev.target.value) }} />
            <select className="App-margin" defaultValue={op} onChange={(ev) => { selectOp(ev.target.value) }}>
                <option value={Op.ADD}> + </option>
                <option value={Op.SUBTRACT}> - </option>
            </select>
            <input type="number" defaultValue={arg2} onChange={(ev) => { update2(ev.target.value) }} />
        </React.Fragment>
    )
}

export default Calc

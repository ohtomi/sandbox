import React from 'react'

import { Op } from '../../calc/constants'

const Calc = ({ state, actions }) => {
    return (
        <React.Fragment>
            <input type="number" value={state.calc.answer} />
            <span className="App-margin">=</span>
            <input type="number" value={state.calc.arg1} onChange={(ev) => { actions.calc.update1(ev.target.value) }} />
            <select className="App-margin" value={state.calc.op} onChange={(ev) => { actions.calc.selectOp(ev.target.value) }}>
                <option value={Op.ADD}> + </option>
                <option value={Op.SUBTRACT}> - </option>
            </select>
            <input type="number" value={state.calc.arg2} onChange={(ev) => { actions.calc.update2(ev.target.value) }} />
        </React.Fragment>
    )
}

export default Calc

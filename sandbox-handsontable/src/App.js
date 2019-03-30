import React, {useRef, useState} from 'react'
import {HotTable} from '@handsontable/react'
import './App.css'
import 'handsontable/dist/handsontable.full.css'

const a_plus_b = (record) => record.A + record.B
const a_minus_b = (record) => record.A - record.B
const a_multiply_b = (record) => record.A * record.B
const a_divide_b = (record) => record.A / record.B

const columns = [
    {data: 'ID', type: 'text'},
    {data: 'A', type: 'numeric'},
    {data: 'B', type: 'numeric'},
    {data: a_plus_b, type: 'numeric'},
    {data: a_minus_b, type: 'numeric'},
    {data: a_multiply_b, type: 'numeric'},
    {data: a_divide_b, type: 'numeric'}
]

const colHeaders = ['ID', 'A', 'B', 'A + B', 'A - B', 'A * B', 'A / B']

const App = () => {
    const [data, setData] = useState([
        {ID: 20, A: 1000, B: 200},
        {ID: 19, A: 1000, B: 200},
        {ID: 18, A: 1000, B: 200},
        {ID: 17, A: 1000, B: 200},
        {ID: 16, A: 1000, B: 200}
    ])
    const onClick = (ev) => {
        const newData = data.map((d) => Object.assign(d, {ext1: '', ext2: '', ext3: '', ext4: ''}))
        setData(newData)
    }

    const ref = useRef(null)
    const afterLoadData = () => {
        if (!ref.current || !ref.current.hotInstance) {
            return
        }
        const plugin = ref.current.hotInstance.getPlugin('ColumnSorting')
        if (!plugin) {
            return
        }
        const sc = plugin.getSortConfig()
        if (!sc) {
            return
        }
        plugin.sort(sc)
    }

    return (
        <div className="App">
            <HotTable
                ref={ref}
                colHeaders={colHeaders}
                columns={columns}
                data={data}
                columnSorting={true}
                manualColumnMove={true}
                afterLoadData={afterLoadData}
            />
            <hr/>
            <button onClick={onClick}>load</button>
        </div>
    )
}

export default App

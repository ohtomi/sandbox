import React, {useRef, useState} from 'react'
import {HotTable} from '@handsontable/react'
import './App.css'
import 'handsontable/dist/handsontable.full.css'

const fn = (record) => {
    return record ? `${record.id}__${record.name}` : '(n/a)'
}

const columns = [
    {data: 'id', type: 'text'},
    {data: 'name', type: 'text'},
    {data: 'age', type: 'text'},
    {data: 'gender', type: 'text'},
    {data: fn, type: 'text'},
    {data: 'registered_at', type: 'text'},
    {data: 'registered_by', type: 'text'},
    {data: 'modified_at', type: 'text'},
    {data: 'modified_by', type: 'text'}
]

const colHeaders = ['id', 'name', 'age', 'gender', 'fn()', 'reg at', 'reg by', 'mod at', 'mod by']

const App = () => {
    const [data, setData] = useState([
        {id: 11111, name: 22222, age: 33333, gender: 44444},
        {id: 11112, name: 22222, age: 33333, gender: 44444},
        {id: 11113, name: 22222, age: 33333, gender: 44444},
        {id: 11114, name: 22222, age: 33333, gender: 44444},
        {id: 11115, name: 22222, age: 33333, gender: 44444},
        {id: 11116, name: 22222, age: 33333, gender: 44444},
        {id: 11117, name: 22222, age: 33333, gender: 44444},
        {id: 11118, name: 22222, age: 33333, gender: 44444},
        {id: 11119, name: 22222, age: 33333, gender: 44444},
        {id: 11120, name: 22222, age: 33333, gender: 44444},
        {id: 11121, name: 22222, age: 33333, gender: 44444},
        {id: 11122, name: 22222, age: 33333, gender: 44444},
        {id: 11123, name: 22222, age: 33333, gender: 44444},
        {id: 11124, name: 22222, age: 33333, gender: 44444},
        {id: 11125, name: 22222, age: 33333, gender: 44444},
        {id: 11126, name: 22222, age: 33333, gender: 44444},
        {id: 11127, name: 22222, age: 33333, gender: 44444},
        {id: 11128, name: 22222, age: 33333, gender: 44444},
        {id: 11129, name: 22222, age: 33333, gender: 44444},
        {id: 11130, name: 22222, age: 33333, gender: 44444}
    ])
    const onClick = (ev) => {
        const newData = data.map((d) => Object.assign(d, {age: d.age + 1}))
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
                afterLoadData={afterLoadData}
            />
            <hr/>
            <button onClick={onClick}>load</button>
        </div>
    )
}

export default App

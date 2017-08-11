import React from 'react';
import {storiesOf} from '@storybook/react';
import {action} from '@storybook/addon-actions';

import HotTable from 'react-handsontable';


class ExampleComponent extends React.Component {

    constructor(props) {
        super(props);

        this.data = [
            {'id': 1, 'name': 'ford', 'year': 2018, 'volume': 1000, 'good': true},
            {'id': 2, 'name': 'volvo', 'year': 2017, 'volume': 1000, 'good': false},
            {'id': 3, 'name': 'toyota', 'year': 2016, 'volume': 1000, 'good': true},
            {'id': 4, 'name': 'honda', 'year': 2015, 'volume': 1000, 'good': true},
        ];
        this.columns = [
            {data: 'id', type: 'numeric', width: 120, readOnly: true},
            {data: 'name', type: 'text', width: 120, readOnly: true},
            {data: 'year', type: 'numeric', width: 120, readOnly: true},
            {data: 'volume', type: 'numeric', width: 120, readOnly: true},
            {data: data => data.good ? 'GOOO' : 'BAD', type: 'text', width: 120, readOnly: true}
        ];
        this.colHeaders = ['ID', 'NAME', 'YEAR', 'VOLUME', 'GOOD/BAD'];

        this.state = {
            columnSorting: true,
            hiddenColumns: []
        };
    }

    afterColumnSort(column, sortOrder) {
        if (this.state.columnSorting.column === column && this.state.columnSorting.sortOrder === sortOrder) {
            return;
        }

        if (sortOrder !== undefined) {
            this.setState({
                columnSorting: {
                    column: column,
                    sortOrder: sortOrder
                }
            });
        } else {
            this.setState({
                columnSorting: {
                    column: column,
                    sortOrder: null
                }
            });
        }
    }

    beforeColumnMove(columns, target) {
        // columns are an array of visual column indexes.
        // target is visual column index.
        action('before column move')(columns.join(', '), target);

        if (!this.state.columnSorting) {
            return;
        }

        const from = columns[0];
        const to = columns[columns.length - 1];

        if (this.state.columnSorting < from && this.state.columnSorting < target) {
            return;
        }
        if (this.state.columnSorting > to && this.state.columnSorting > target) {
            return;
        }

        const rangeLength = to < target ? target : to + 1;
        const range = Array.from({length: rangeLength}, (v, k) => k);

        if (to < target) {
            range.splice(target, 0, ...range.slice(from, to + 1));
            range.splice(from, to + 1 - from);
        } else if (from > target) {
            range.splice(target, 0, ...range.splice(from, to + 1 - from));
        }
        action('range')(range.join(', '));

        let newSortIndex = this.state.columnSorting.column;
        range.forEach((column, index) => {
            if (this.state.columnSorting.column === column) {
                newSortIndex = index;
            }
        });
        action('sort index')(`${this.state.columnSorting.column} -> ${newSortIndex}`);

        this.setState({
            columnSorting: {
                column: newSortIndex,
                sortOrder: this.state.columnSorting.sortOrder
            }
        });
    }

    afterColumnMove(columns, target) {
        // columns are an array of physical column indexes. document bug?
        // target is visual column index.
        action('after column move')(columns.join(', '), target);
    }

    afterColumnResize(column, width, isDoubleClick) {
        action('after column resize')(column, width, isDoubleClick);

        this.hot.hotInstance.updateSettings({});
    }

    afterUpdateSettings(settings) {
        requestAnimationFrame(() => {
            const tables = document.querySelectorAll('.htCore');

            const countCols = this.hot.hotInstance.countCols();
            const range = Array.from({length: countCols}, (v, k) => k);

            range.forEach(column => {
                tables.forEach((table, index) => {
                    const hidden = this.state.hiddenColumns.some(hidden => column === hidden);
                    const display = hidden ? 'none' : '';

                    table.querySelectorAll(`th:nth-child(${column + 1})`).forEach(cell => {
                        cell.style.display = display;
                    });
                    table.querySelectorAll(`td:nth-child(${column + 1})`).forEach(cell => {
                        cell.style.display = display;
                    });
                });
            });
        });
    }

    onClickHideButton() {
        const hiddenColumns = this.state.hiddenColumns.slice();
        hiddenColumns.push(parseInt(this.input.value));

        this.setState({hiddenColumns: hiddenColumns});
        // this.hot.hotInstance.updateSettings({});
    }

    onClickShowButton() {
        const inputValue = parseInt(this.input.value);
        const hiddenColumns = this.state.hiddenColumns.filter(hidden => inputValue !== hidden);

        this.setState({hiddenColumns: hiddenColumns});
        // this.hot.hotInstance.updateSettings({});
    }

    render() {
        return (
            <div id="example-component">
                <HotTable ref={hot => this.hot = hot}
                          root="hot"
                          data={this.data}
                          columns={this.columns} colHeaders={this.colHeaders}
                          columnSorting={this.state.columnSorting} sortIndicator={true}
                          manualColumnMove={true} manualColumnResize={true}
                          width="600" height="150"
                          afterColumnSort={this.afterColumnSort.bind(this)}
                          beforeColumnMove={this.beforeColumnMove.bind(this)}
                          afterColumnMove={this.afterColumnMove.bind(this)}
                          afterColumnResize={this.afterColumnResize.bind(this)}
                          afterUpdateSettings={this.afterUpdateSettings.bind(this)}/>
                <hr/>
                <input ref={input => this.input = input} type="number" min={0} max={4} defaultValue={0} size={10}/>
                <button onClick={this.onClickHideButton.bind(this)}>hide column</button>
                <button onClick={this.onClickShowButton.bind(this)}>show column</button>
            </div>
        );
    }
}

storiesOf('Handsontable', module)
    .add('move and sort', () => (
        <ExampleComponent/>
    ));

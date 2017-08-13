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
            data: this.data,
            maxRows: this.data.length,
            columnSorting: true,
            hiddenColumns: []
        };
    }

    afterColumnSort(column, sortOrder) {
        // column is visual column index.
        action('after column sort')(column, sortOrder);

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

        this.hot.hotInstance.updateSettings({});
    }

    afterColumnResize(column, width, isDoubleClick) {
        // column is visual column index.
        action('after column resize')(column, width, isDoubleClick);

        this.hot.hotInstance.updateSettings({});
    }

    afterUpdateSettings(settings) {
        requestAnimationFrame(() => {
            const tables = document.querySelectorAll('.htCore');

            const countCols = this.hot.hotInstance.countCols();
            const range = Array.from({length: countCols}, (v, k) => k);

            range.forEach(column => {

                const hidden = this.state.hiddenColumns.some(hidden => {
                    const visual = this.hot.hotInstance.toVisualColumn(hidden);
                    return visual === column;
                });

                tables.forEach((table, index) => {
                    table.querySelectorAll(`col:nth-child(${column + 1})`).forEach(cell => {
                        if (hidden) {
                            cell.classList.add('hidden');
                        } else {
                            cell.classList.remove('hidden');
                        }
                    });
                    table.querySelectorAll(`th:nth-child(${column + 1})`).forEach(cell => {
                        if (hidden) {
                            cell.classList.add('hidden');
                        } else {
                            cell.classList.remove('hidden');
                        }
                    });
                    table.querySelectorAll(`td:nth-child(${column + 1})`).forEach(cell => {
                        if (hidden) {
                            cell.classList.add('hidden');
                        } else {
                            cell.classList.remove('hidden');
                        }
                    });
                });
            });
        });
    }

    onClickMoveButton() {
        const plugin = this.hot.hotInstance.getPlugin('ManualColumnMove');
        action('debug')('plugin?', !!plugin);

        if (plugin) {
            plugin.moveColumn(3, 5);
        }
    }

    onClickHideButton() {
        const hiddenColumns = this.state.hiddenColumns.slice();
        hiddenColumns.push(parseInt(this.input.value));

        this.setState({hiddenColumns: hiddenColumns});
    }

    onClickShowButton() {
        const inputValue = parseInt(this.input.value);
        const hiddenColumns = this.state.hiddenColumns.filter(hidden => inputValue !== hidden);

        this.setState({hiddenColumns: hiddenColumns});
    }

    onClickAddDataButton() {
        const data = this.state.data.slice();
        data.push({
            'id': 11 + (this.state.data.length - 4),
            'name': 'new ford',
            'year': 2019 + (this.state.data.length - 4),
            'volume': 1000,
            'good': true
        });

        this.setState({data: data, maxRows: data.length});
    }

    componentDidMount() {
        const plugin = this.hot.hotInstance.getPlugin('ManualColumnMove');
        action('debug')('plugin?', !!plugin);

        if (plugin) {
            const fn = (from, to) => {
                let width = 0;

                for (let i = from; i < to; i++) {
                    let columnWidth = 0;

                    const hidden = this.state.hiddenColumns.some(hidden => {
                        const visual = this.hot.hotInstance.toVisualColumn(hidden);
                        return visual === i;
                    });

                    if (hidden) {
                        columnWidth = 0;
                    } else if (i < 0) {
                        columnWidth = this.hot.hotInstance.view.wt.wtTable.getColumnWidth(i) || 0;
                    } else {
                        columnWidth = this.hot.hotInstance.view.wt.wtTable.getStretchedColumnWidth(i) || 0;
                    }

                    width += columnWidth;
                }

                return width;
            };

            plugin.getColumnsWidth = fn.bind(this);
        }
    }

    render() {
        return (
            <div id="example-component">
                <HotTable ref={hot => this.hot = hot}
                          root="hot"
                          data={this.state.data} maxRows={this.state.maxRows}
                          columns={this.columns} colHeaders={this.colHeaders}
                          columnSorting={this.state.columnSorting} sortIndicator={true}
                          manualColumnMove={true} manualColumnResize={true}
                          width="600" height="300"
                          afterColumnSort={this.afterColumnSort.bind(this)}
                          beforeColumnMove={this.beforeColumnMove.bind(this)}
                          afterColumnMove={this.afterColumnMove.bind(this)}
                          afterColumnResize={this.afterColumnResize.bind(this)}
                          afterUpdateSettings={this.afterUpdateSettings.bind(this)}/>
                <hr/>
                <button onClick={this.onClickMoveButton.bind(this)}>move column</button>
                {' | '}
                <input ref={input => this.input = input} type="number" min={0} max={4} defaultValue={3} size={10}/>
                <button onClick={this.onClickHideButton.bind(this)}>hide column</button>
                <button onClick={this.onClickShowButton.bind(this)}>show column</button>
                {' | '}
                <button onClick={this.onClickAddDataButton.bind(this)}>add data</button>
            </div>
        );
    }
}

storiesOf('Handsontable', module)
    .add('move, sort and hide', () => (
        <ExampleComponent/>
    ));

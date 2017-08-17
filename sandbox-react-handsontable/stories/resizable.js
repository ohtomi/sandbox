import React from 'react';
import Resizable from 'react-resizable-box';
import {storiesOf} from '@storybook/react';
import {action} from '@storybook/addon-actions';


storiesOf('Resizable-Box', module)
    .add('plain', () => (
        <Resizable
            className="item"
            width={320}
            height={200}
        >
            Basic Sample
        </Resizable>
    ));

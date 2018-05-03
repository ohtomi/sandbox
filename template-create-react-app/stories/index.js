import React from 'react'
import { storiesOf } from '@storybook/react'
import { action } from '@storybook/addon-actions'
import App from '../src/App'

storiesOf('Button', module)
    .add('with some emoji', () => (
        <button onClick={action('clicked')}><span role="img" aria-label="so cool">😀 😎 👍 💯</span></button>
    ))

storiesOf('App', module)
    .add('default', () => (
        <App />
    ))

import { configure } from '@storybook/react';
import '../css/page.css';

function loadStories() {
    require('../stories/index.js');
}

configure(loadStories, module);

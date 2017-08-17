import { configure } from '@storybook/react';
import '../css/page.css';

function loadStories() {
    require('../stories/index.js');
    require('../stories/resizable.js');
}

configure(loadStories, module);

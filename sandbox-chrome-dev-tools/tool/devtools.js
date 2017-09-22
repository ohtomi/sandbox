console.warn('devtools.js');

chrome.devtools.panels.create(
    'Sample',
    '',
    './panel.html',
    (panel) => {
        let _panelWindow;

        // a-0. connect to background
        const port = chrome.runtime.connect({
            name: 'devtools-page'
        });

        const onShown = (panelWindow) => {
            console.warn('on shown!', panelWindow);

            panel.onShown.removeListener(onShown);

            // a-1. hold window reference to panel
            _panelWindow = panelWindow;

            // a-2. tab id to background
            port.postMessage({
                tabId: chrome.devtools.inspectedWindow.tabId
            });
        };

        port.onMessage.addListener((message) => {
            console.warn('from background.js to devtools.js', message);

            // b-4. pass through message from panel to background
            if (_panelWindow) {
                _panelWindow.notifyBackgroundMessage(message);
            }
        });

        panel.onShown.addListener(onShown);
    }
);
console.warn('devtools.js');

chrome.devtools.panels.create(
    'TWDetector',
    '',
    './panel.html',
    (panel) => {
        let _panelWindow;

        const backgroundPageConnection = chrome.runtime.connect({
            name: 'devtools-page'
        });

        backgroundPageConnection.onMessage.addListener((message) => {
            console.warn('from background.js', message);
            if (_panelWindow) {
                _panelWindow.notifyBackgroundMessage(message);
            }
        });

        const onShown = (panelWindow) => {
            panel.onShown.removeEventListener(onShown);
            _panelWindow = panelWindow;
            _panelWindow.respond = (message) => {
                backgroundPageConnection.postMessage(message);
            };
        };

        // chrome.runtime.sendMessage({
        //     tabId: chrome.devtools.inspectedWindow.tabId
        // });
    }
);
console.warn('devtools.js');

chrome.devtools.panels.create(
    'sample',
    '',
    './panel.html',
    (panel) => {
        let _panelWindow;

        // a-0. connect to background
        const backgroundPageConnection = chrome.runtime.connect({
            name: 'devtools-page'
        });

        // a-4. pass through message from panel
        backgroundPageConnection.onMessage.addListener((message) => {
            console.warn('from background.js to devtools.js', message);
            if (_panelWindow) {
                _panelWindow.notifyBackgroundMessage(message);
            }
        });

        // a-2. pass through message from panel
        const onShown = (panelWindow) => {
            panel.onShown.removeListener(onShown);
            _panelWindow = panelWindow;
            _panelWindow.respond = (message) => {
                backgroundPageConnection.postMessage({
                    message: message,
                    tabId: chrome.devtools.inspectedWindow.tabId
                });
            };
        };

        panel.onShown.addListener(onShown);

        // chrome.runtime.sendMessage({
        //     tabId: chrome.devtools.inspectedWindow.tabId
        // });
    }
);
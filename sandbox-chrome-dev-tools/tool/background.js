console.warn('background.js');

chrome.runtime.onConnect.addListener((devToolsConnection) => {
    const devToolsListener = (message, sender, sendResponse) => {
        console.log('from panel.js via devtools.js to background.js', message);

        chrome.tabs.executeScript(message.tabId, {
            file: 'content.js'
        });

        // a-3. message to devtools
        devToolsConnection.postMessage({
            message: message,
            receivedAt: new Date()
        })
    };

    devToolsConnection.onMessage.addListener(devToolsListener);

    devToolsConnection.onDisconnect.addListener(function () {
        devToolsConnection.onMessage.removeListener(devToolsListener);
    });
});

// b-3. dump message
chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
    console.log('xxx', request, sender, sendResponse);
});
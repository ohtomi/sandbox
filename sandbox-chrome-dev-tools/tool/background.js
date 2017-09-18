console.warn('background.js');

chrome.runtime.onConnect.addListener((devToolsConnection) => {
    const devToolsListener = (message, sender, sendResponse) => {
        console.log('from panel.js via devtools.js to background.js', message);
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
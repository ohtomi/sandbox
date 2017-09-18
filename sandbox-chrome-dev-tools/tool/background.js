console.warn('background.js');

chrome.runtime.onConnect.addListener((devToolsConnection) => {
    const devToolsListener = (message, sender, sendResponse) => {
        console.log('from devtools.js', message);
        devToolsConnection.postMessage({
            message: message,
            receivedAt: new Date().getTime()
        })
    };

    devToolsConnection.onMessage.addListener(devToolsListener);

    devToolsConnection.onDisconnect.addListener(function () {
        devToolsConnection.onMessage.removeListener(devToolsListener);
    });
});
console.warn('background.js');

const connections = {};

chrome.runtime.onConnect.addListener((port) => {
    const onMessage = (message, sender, sendResponse) => {
        console.warn('from panel.js via devtools.js to background.js', message);

        // b-0. inject content.js
        chrome.tabs.executeScript(message.tabId, {
            file: 'content.js'
        });

        // a-3. hold connection to devtools corresponds to tab id
        connections[message.tabId] = port;
    };

    port.onMessage.addListener(onMessage);

    port.onDisconnect.addListener(function () {
        port.onMessage.removeListener(onMessage);

        const tabs = Object.keys(connections);
        for (var i = 0, len = tabs.length; i < len; i++) {
          if (connections[tabs[i]] === port) {
            delete connections[tabs[i]]
            break;
          }
        }
    });
});

chrome.runtime.onMessage.addListener((message, sender, sendResponse) => {
    console.warn('from page.js via content.js to background.js', message);

    // b-3. message to devtools
    if (sender.tab) {
        const port = connections[sender.tab.id];
        if (port) {
            port.postMessage(message);
        }
    }
});
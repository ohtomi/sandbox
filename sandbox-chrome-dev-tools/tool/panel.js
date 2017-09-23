console.warn('panel.js');

const messagesEl = document.getElementById('messages');

// chrome.devtools.network.onRequestFinished.addListener((req) => {
//     const url = req.request.url;
//     if (url.indexOf('twitter') !== -1) {
//         console.log(`req:${JSON.stringify(req)}`);
//         document.getElementById('message').innerText = 'このサイトから、Twitterの香りがするでゲソ';
//     }
// });

chrome.devtools.network.onNavigated.addListener((url) => {
    console.clear();
    messagesEl.textContent = '';
});

// b-5. dump message
function notifyBackgroundMessage(message) {
    console.warn('from devtools.js to panel.js', message);
    const messageEl = document.createElement('p');
    messageEl.textContent = JSON.stringify(message);
    messagesEl.appendChild(messageEl);
}
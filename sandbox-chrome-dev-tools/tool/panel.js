console.warn('panel.js');

// chrome.devtools.network.onRequestFinished.addListener((req) => {
//     const url = req.request.url;
//     if (url.indexOf('twitter') !== -1) {
//         console.log(`req:${JSON.stringify(req)}`);
//         document.getElementById('message').innerText = 'このサイトから、Twitterの香りがするでゲソ';
//     }
// });

// chrome.devtools.network.onNavigated.addListener((url) => {
//     document.getElementById('message').innerText = '';
//     console.clear();
// });

// b-5. dump message
function notifyBackgroundMessage(message) {
    console.warn('from devtools.js to panel.js', message);
}
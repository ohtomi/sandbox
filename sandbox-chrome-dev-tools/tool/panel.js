console.warn('panel.js');

chrome.devtools.network.onRequestFinished.addListener((req) => {
    const url = req.request.url;
    if (url.indexOf('twitter') !== -1) {
        console.log(`req:${JSON.stringify(req)}`);
        document.getElementById('message').innerText = 'このサイトから、Twitterの香りがするでゲソ';
    }
});

chrome.devtools.network.onNavigated.addListener((url) => {
    document.getElementById('message').innerText = '';
    console.clear();
});

function notifyBackgroundMessage(message) {
    console.log('from devtools.js to panel.js', message);
}

document.documentElement.onclick = () => {
    respond({
        text: 'foo bar baz!',
        sentAt: new Date()
    });
};
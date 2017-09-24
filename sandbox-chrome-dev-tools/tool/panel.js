console.warn('panel.js');

const messagesEl = document.getElementById('messages');

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
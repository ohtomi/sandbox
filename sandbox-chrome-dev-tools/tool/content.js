console.warn('content.js');

// b-2. message to background
document.addEventListener('hello', function(data) {
    chrome.runtime.sendMessage({
        text: 'hoge fuga',
        sentAt: Date.now()
    });
});
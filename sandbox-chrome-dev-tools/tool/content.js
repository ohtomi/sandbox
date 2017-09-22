console.warn('content.js');

// b-2. message to background
document.addEventListener('hello', (event) => {
    console.warn('on event!', event);

    chrome.runtime.sendMessage({
        event: JSON.stringify(event.detail),
        sentAt: Date.now()
    });
});
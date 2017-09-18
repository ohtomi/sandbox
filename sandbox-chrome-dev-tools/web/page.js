setInterval(() => {
    if (chrome.runtime) {
        const message = {
            text: 'from content',
            at: new Date()
        };
        chrome.runtime.sendMessage('devtools-page', message);
        console.warn('sent', message);
    }
}, 1000);
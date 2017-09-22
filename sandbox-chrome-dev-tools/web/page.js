console.warn('page.js');

// b-1. fire event
document.getElementById('btn').addEventListener('click', () => {
    const event = new CustomEvent('hello', {detail: {
        greet: 'こんにちは',
        fireAt: Date.now()
    }});

    console.warn('triggering event...', event);
    document.dispatchEvent(event);
});
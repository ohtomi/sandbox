console.warn('page.js');

// b-1. fire event
setInterval(() => {
    const event = document.createEvent('Event');
    event.initEvent('hello');
    document.dispatchEvent(event);
}, 1000);
import React from 'react'

const Link = ({ actions: { routing }, replace, dispatch, to, children }) => {
    const onClick = (ev) => {
        ev.preventDefault();
        if (replace) {
            routing.replaceHistory(to);
        } else {
            routing.pushHistory(to);
        }
    };
    return (
        <a href="" onClick={onClick}>
            {children}
        </a>
    );
}

export default Link

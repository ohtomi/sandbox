import PageRoot from './components/PageRoot';
import PageFoo from './components/PageFoo';
import PageBar from './components/PageBar';

const routes = {
    path: '/',
    name: 'root',
    children: [{
        path: '/foo/:name',
        name: 'foo',
        action(context, params) {
            return { Component: PageFoo };
        }
    }, {
        path: '/bar',
        name: 'bar',
        action(context) {
            return { Component: PageBar };
        }
    }],
    async action(context) {
        const route = await context.next();
        return route || { Component: PageRoot };
    }
};

export default routes;

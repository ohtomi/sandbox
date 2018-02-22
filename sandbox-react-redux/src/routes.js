import PageRoot from './components/pages/PageRoot'
import PageFoo from './components/pages/PageFoo'
import PageBar from './components/pages/PageBar'

const routes = {
    path: '/',
    name: 'root',
    children: [{
        path: '/foo/:name',
        name: 'foo',
        action(context, params) {
            return { Component: PageFoo }
        }
    }, {
        path: '/bar',
        name: 'bar',
        action(context) {
            return { Component: PageBar }
        }
    }],
    async action(context) {
        const route = await context.next()
        return route || { Component: PageRoot }
    }
}

export default routes

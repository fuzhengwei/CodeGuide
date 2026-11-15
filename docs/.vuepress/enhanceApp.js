import Mermaid from './components/Mermaid'

export default ({router, Vue}) => {
    // 注册 mermaid 渲染组件（修复官方插件只取 slot 第一个 text 节点导致图源码被截断的问题）
    Vue.component('mermaid', Mermaid)
    /**
     * 路由切换事件处理
     */
    router.beforeEach((to, from, next) => {
        //触发百度的pv统计
        if (typeof _hmt != "undefined") {
            if (to.path) {
                _hmt.push(["_trackPageview", to.fullPath]);
            }
        }
        // continue
        next();
    });
};
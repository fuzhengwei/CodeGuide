// mermaid 支持（本地版）
// fence 规则取自 vuepress-plugin-mermaidjs，组件用 docs/.vuepress/components/Mermaid.vue
// （官方插件的组件只取 slot 第一个 text 节点，图源码含引号被 Vue 拆分后会截断，故本地重写组件）
const path = require('path')

module.exports = (options, ctx) => {
  return {
    extendMarkdown: md => {
      const fence = md.renderer.rules.fence;
      md.renderer.rules.fence = (...args) => {
        const [tokens, idx] = args;
        const token = tokens[idx];
        if (token.info === 'mermaid') {
          return `<mermaid>${token.content}</mermaid>`
        } else {
          const rawCode = fence(...args);
          return `${rawCode}`
        }
      };
    }
  }
}

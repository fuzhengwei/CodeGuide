<template>
  <div class="mermaid-diagram" v-html="svg"></div>
</template>

<script>
let graphIdCounter = 0

export default {
  name: 'Mermaid',
  data() {
    return {
      svg: '',
      graph: ''
    }
  },
  created() {
    // 官方 vuepress-plugin-mermaidjs 只取 $slots.default[0].text，
    // Vue 模板编译会把含引号等特殊字符的图源码拆成多个 text 节点，导致源码被截断解析报错。
    // 这里合并全部 slot 文本节点，得到完整的 mermaid 源码。
    if (this.$slots.default) {
      this.graph = this.$slots.default
        .map(vnode => (typeof vnode.text === 'string' ? vnode.text : ''))
        .join('')
        .trim()
    }
  },
  mounted() {
    if (this.graph.length) {
      this.renderSVG()
    }
  },
  methods: {
    renderSVG() {
      import('mermaid/dist/mermaid').then(mermaid => {
        mermaid.initialize({ startOnLoad: false, theme: 'default' })
        // 注意：不能用 display:none —— mermaid 布局引擎(dagre)依赖容器做文本测量，
        // 隐藏容器测出尺寸为 0，导致布局坐标 undefined、图形塌缩。
        // 官方做法：绝对定位移出视野，保持"可见"以参与测量。
        const renderDiv = document.createElement('div')
        renderDiv.setAttribute(
          'style',
          'position: absolute; left: -100000px; top: -100000px; height: 100%; width: 100%;'
        )
        document.body.appendChild(renderDiv)
        try {
          mermaid.render(
            'mermaid' + ++graphIdCounter,
            this.graph,
            svg => {
              this.svg = svg
              if (renderDiv.parentNode) document.body.removeChild(renderDiv)
            },
            renderDiv
          )
        } catch (e) {
          console.error('[mermaid] render failed:', e && e.message)
          this.svg = ''
          if (renderDiv.parentNode) document.body.removeChild(renderDiv)
        }
      })
    }
  }
}
</script>

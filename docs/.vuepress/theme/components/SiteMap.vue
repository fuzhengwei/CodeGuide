<template>
    <table v-if="data.navlist && data.navlist.length" class="sitemap-table">
      <tr v-for="(navitem, index) in data.navlist" :key="index">
        <td nowrap><div class="sitemap-col-group">{{ navitem.title }}</div></td>
        <td v-if="0==navitem.linkMode" >
          <div class="sitemap-col-item" v-for="item in navitem.list" :key="item.url">
            <a :href="item.url" target="_blank" :title="item.title">  
              {{ item.title || '' }}
              <svg xmlns="http://www.w3.org/2000/svg" aria-hidden="true" x="0px" y="0px" viewBox="0 0 100 100" width="15" height="15" class="icon outbound"><path fill="currentColor" d="M18.8,85.1h56l0,0c2.2,0,4-1.8,4-4v-32h-8v28h-48v-48h28v-8h-32l0,0c-2.2,0-4,1.8-4,4v56C14.8,83.3,16.6,85.1,18.8,85.1z"></path> <polygon fill="currentColor" points="45.7,48.7 51.3,54.3 77.2,28.5 77.2,37.2 85.2,37.2 85.2,14.9 62.8,14.9 62.8,22.9 71.5,22.9"></polygon></svg>
            </a>
          </div>
        </td>
        <td v-if="0!=navitem.linkMode" >
            <router-link class="sitemap-col-item" v-for="item in navitem.list" :key="item.url" :to="item.url" >
              {{ item.title || '' }}
            </router-link>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>

export default {
  name: 'SiteMap',
  components: {},
  computed: {
    data() {
      // return this.$frontmatter
      return {
        navlist: [
          {
            title: '常用搜索',
            linkMode: 0,
            list: [
              {
                title: '百度',
                img: '/_images/tool-logo/baidu.jpg',
                des: '百度——全球最大的中文搜索引擎及最大的中文网站，全球领先的人工智能公司。',
                url: 'http://www.baidu.com/'
              },
              {
                title: 'Google',
                img: '/_images/tool-logo/google.png',
                des: '全球最大的搜索引擎公司',
                url: 'http://www.google.com/'
              },
              {
                title: 'Bing',
                img: '/_images/tool-logo/bing.jpg',
                des: '微软公司推出的用以取代Live Search的搜索引擎',
                url: 'http://www.bing.com/'
              },
              {
                title: 'Github',
                img: '/_images/tool-logo/github.jpg',
                des: 'GitHub是一个面向开源及私有软件项目的托管平台',
                url: 'https://github.com'
              },
              {
                title: '搜代码',
                img: '/_images/tool-logo/java.jpg',
                des: '不知道某个类如何使用? 来这里搜索Java 代码吧...',
                url: 'https://www.programcreek.com/java-api-examples/index.php'
              }
              
            ]
          },
          {
            title: '技术社区',
            linkMode: 0,
            list: [
              {
                title: 'CDSN',
                img: '/_images/tool-logo/csdn.gif',
                des: '中国专业IT社区CSDN',
                url: 'http://www.csdn.net/'
              },
              {
                title: '博客园',
                img: '/_images/tool-logo/cnblog.gif',
                des: '开发者的网上家园',
                url: 'http://www.cnblogs.com/'
              },
              {
                title: 'OSChina',
                img: '/_images/tool-logo/oschina.gif',
                des: '中国最大的开源技术社区',
                url: 'https://www.oschina.net'
              },
              {
                title: '知否',
                img: '/_images/tool-logo/segmentfault.png',
                des: '中国领先的开发者技术社区',
                url: 'https://segmentfault.com/'
              },
              {
                title: '掘金',
                img: '/_images/tool-logo/juejin.svg',
                des: '掘金是一个帮助开发者成长的社区，是一个面向互联网技术人的内容分享平台',
                url: 'https://juejin.im'
              },
              {
                title: 'Linux公社',
                img: '/_images/tool-logo/linuxidc.jpg',
                des: 'Linux系统门户网站',
                url: 'https://www.linuxidc.com/'
              },
              {
                title: 'IBM 开发者',
                img: '/_images/tool-logo/ibm.jpeg',
                des: 'IBM开发者社区',
                url: 'https://www.ibm.com/developerworks/cn/'
              },
              {
                title: 'StackOverflow',
                img: '/_images/tool-logo/stackoverflow.png',
                des: 'Stack Overflow是一个与程序相关的IT技术问答网站',
                url: 'https://stackoverflow.com'
              }
              
            ]
          },
          {
            title: 'Java相关',
            list: [
              {
                title: '面向对象基础',
                url: '/md/java/basic/java-basic-oop.html'
              },
              {
                title: '语法基础',
                url: '/md/java/basic/java-basic-lan-basic.html'
              },
              {
                title: '集合框架',
                url: '/md/java/collection/java-collection-all.html'
              },
              {
                title: '并发基础',
                url: '/md/java/thread/java-thread-x-overview.html'
              },
              {
                title: '并发关键字',
                url: '/md/java/thread/java-thread-x-key-synchronized.html'
              },
              {
                title: 'JUC并发框架',
                url: '/md/java/thread/java-thread-x-juc-overview.html'
              },
              {
                title: 'IO框架',
                url: '/md/java/io/java-io-overview.html'
              },
              {
                title: 'Java8 特性',
                url: '/md/java/java8/java8.html'
              },
              {
                title: 'JVM基础',
                url: '/md/java/jvm/java-jvm-x-overview.html'
              },
              {
                title: '调试排错',
                url: '/md/java/jvm/java-jvm-param.html'
              },
              {
                title: '更多资源',
                url: '/md/java/others/java-others-awesome-java.html'
              },
            ]
          },
          {
            title: '算法相关',
            list: [
              {
                title: '数组与线性表',
                url: '/md/algorithm/alg-basic-array.html'
              },
              {
                title: '树详解',
                url: '/md/algorithm/alg-basic-tree.html'
              },
              {
                title: '图详解',
                url: '/md/algorithm/alg-basic-graph.html'
              },
              {
                title: '内部排序',
                url: '/md/algorithm/alg-sort-overview.html'
              },
              {
                title: '算法思想',
                url: '/md/algorithm/alg-core-divide-and-conquer.html'
              },
              {
                title: '安全算法',
                url: '/md/algorithm/alg-domain-security-degist.html'
              },
              {
                title: '大数据处理',
                url: '/md/algorithm/alg-domain-bigdata-overview.html'
              },
              {
                title: '分布式算法',
                url: '/md/algorithm/alg-domain-distribute-overview.html'
              },
              {
                title: '负载均衡算法',
                url: '/md/algorithm/alg-domain-load-balance.html'
              },
              {
                title: '推荐算法',
                url: '/md/algorithm/alg-domain-suggest.html'
              },
              {
                title: '头脑风暴',
                url: '/md/algorithm/alg-other-mind.html'
              },
            ]
          },
          {
            title: '数据库相关',
            list: [
              {
                title: '数据库原理',
                url: '/md/db/sql/sql-db-howitworks.html'
              },
              {
                title: 'SQL语言',
                url: '/md/db/sql-lan/sql-lan.html'
              },
              {
                title: 'MySQL相关',
                url: '/md/db/sql-mysql/sql-mysql-theory.html'
              },
              {
                title: 'MongoDB',
                url: '/md/db/nosql-mongo/mongo-performance-improve.html'
              },
              {
                title: 'ElasticSearch',
                url: '/md/db/nosql-es/elasticsearch.html'
              }
            ]
          },
          {
            title: '开发基础相关',
            list: [
              {
                title: '常用类库',
                url: '/md/develop/package/dev-package-x-apache-common.html'
              },
              {
                title: '单元测试',
                url: '/md/develop/ut/dev-ut-x-junit.html'
              },
              {
                title: '正则表达式',
                url: '/md/develop/regex/dev-regex-all.html'
              },
              {
                title: '网络协议',
                url: '/md/develop/protocol/dev-protocol-overview.html'
              },
              {
                title: '安全相关',
                url: '/md/develop/security/dev-security-overview.html'
              },
              {
                title: '常见重构技巧',
                url: '/md/develop/refactor/dev-refactor-if-else.html'
              }
            ]
          }
          ,{
            title: '架构相关',
            list: [
              {
                title: '架构基础',
                url: '/md/arch/arch-x-basic.html'
              },
              {
                title: '架构视角',
                url: '/md/arch/arch-x-view.html'
              },
              {
                title: '架构演进',
                url: '/md/arch/arch-x-evolution.html'
              },
              {
                title: '架构模式和要素',
                url: '/md/arch/arch-x-pattern.html'
              },
              {
                title: '高并发之缓存',
                url: '/md/arch/arch-y-cache.html'
              },
              {
                title: '高并发之限流',
                url: '/md/arch/arch-y-ratelimit.html'
              },
              {
                title: '高并发之降级',
                url: '/md/arch/arch-y-reduce.html'
              },
              {
                title: '负载均衡',
                url: '/md/arch/arch-y-loadbalance.html'
              },
              {
                title: '容灾备份',
                url: '/md/arch/arch-y-backup.html'
              },
              {
                title: '架构安全',
                url: '/md/arch/arch-x-security.html'
              },
              {
                title: '秒杀系统设计',
                url: '/md/arch/arch-example-seckill.html'
              },
              {
                title: '架构案例',
                url: '/md/arch/arch-example-goods-detail.html'
              }
            ]
          }
          ,{
            title: '工具和部署',
            list: [
              {
                title: '工具集合',
                url: '/md/devops/tool/tool-list-overview.html'
              },
              {
                title: 'IDE相关',
                url: '/md/devops/tool/tool-ide.html'
              },
              {
                title: 'Git',
                url: '/md/devops/tool/tool-git.html'
              },
              {
                title: 'Maven',
                url: '/md/devops/tool/tool-maven.html'
              },
              {
                title: 'Linux',
                url: '/md/devops/linux/linux.html'
              },
              {
                title: 'Docker',
                url: '/md/devops/docker/docker-00-overview.html'
              }
            ]
          }
          ,{
            title: '方法论',
            list: [
              {
                title: 'SOLID',
                url: '/md/dev-spec/spec/dev-th-solid.html'
              },
              {
                title: 'CAP',
                url: '/md/dev-spec/spec/dev-th-cap.html'
              },
              {
                title: 'BASE',
                url: '/md/dev-spec/spec/dev-th-base.html'
              },
              {
                title: '开源协议',
                url: '/md/dev-spec/spec/dev-opensource.html'
              },
              {
                title: '代码规范',
                url: '/md/dev-spec/code-style/code-style-alibaba.html'
              },
              {
                title: '设计模式',
                url: '/md/dev-spec/pattern/1_overview.html'
              }
            ]
          },{
            title: '产品和团队',
            list: [
              {
                title: '产品相关',
                url: '/md/team/team-z-wechat.html'
              },
              {
                title: '团队相关',
                url: '/md/team/team-z-task-emergency.html'
              },
              {
                title: '其它分享',
                url: '/md/team/team-z-arch-future.html'
              },
            ]
          }
        ]
      }
    }
  },
  mounted() {
  },
  methods: {}
}
</script>
<style lang='stylus'>
  .sitemap-table
    margin 0
    tr:nth-child(2n)
      background-color: #fff;
    th, td
      border: 1px solid #f6f7f9;
      padding: 5px;
      padding-bottom: 0;

  .sitemap-col-group
    margin-bottom:5px;
    font-size: 12px;
    font-weight: bold;
  
  .sitemap-col-item
    float: left;
    margin-right: 5px;
    font-size: 12px;
    margin-bottom: 5px;
    padding: 3px 8px;
    background-color: #fbfbfb;
    border-radius: 2px;
    border: 1px solid #eee;
    //box-shadow: 0 1px 1px 0 rgba(0, 0, 0, 0.1);
    text-align: center;
    color: #42b983;
    font-weight: 400;
    a
      font-weight: 400;
      color: #42b983;
  .sitemap-col-item:hover
    box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.3);
    color: $accentColor
    a
      color: $accentColor

  
  
</style>

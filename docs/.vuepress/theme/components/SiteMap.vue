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
</template>

<script>

export default {
  name: 'SiteMap',
  components: {},
  computed: {
    data() {
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
                title: '思否',
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
                title: 'PDF 下载',
                linkMode: 0,
                list: [
                    {
                        title: '《Java 面经手册》',
                        url: 'https://download.csdn.net/download/Yao__Shun__Yu/14932325'
                    },
                    {
                        title: '《重学Java设计模式》',
                        url: 'https://download.csdn.net/download/Yao__Shun__Yu/19265731'
                    },
                    {
                        title: '《手撸 Spring》',
                        url: 'https://download.csdn.net/download/Yao__Shun__Yu/21009038'
                    },
                    {
                        title: '《字节码编程》',
                        url: 'https://download.csdn.net/download/Yao__Shun__Yu/12505051'
                    }
                ]
            },
          {
            title: '面试求职',
            list: [
              {
                title: '简历优化',
                url: '/md/about/job/2020-04-11-工作两年简历写成这样，谁要你呀！.html'
              },
              {
                title: '简历筛选',
                url: '/md/about/job/2021-02-24-半年筛选了400+份简历，告诉你怎么写会被撩.html'
              },
              {
                title: '大厂要求',
                url: '/md/about/job/2020-11-15-BATJTMD，大厂招聘，都招什么样Java程序员？.html'
              },
              {
                title: '薪资待遇',
                url: '/md/about/job/2020-12-20-工作3年，看啥资料能月薪30K？.html'
              },
              {
                title: '北漂生活',
                url: '/md/about/me/2020-11-29-北漂码农的我，把在大城市过成了屯子一样舒服，哈哈哈哈哈！.html'
              }
            ]
          },{
            title: 'Java相关',
            list: [
              {
                title: '数据结构和算法',
                url: '/md/java/interview/2020-08-04-面经手册 · 第2篇《数据结构，HashCode为什么使用31作为乘数？》.html'
              },
              {
                title: '并发和锁',
                url: '/md/java/interview/2020-10-14-面经手册 · 第13篇《除了JDK、CGLIB，还有3种类代理方式？面试又卡住！》.html'
              },
              {
                title: '多线程',
                url: '/md/java/interview/2020-11-25-面经手册 · 第19篇《Thread.start()%20，它是怎么让线程启动的呢？》.html'
              },
              {
                title: 'Java8 特性',
                url: '/md/java/core/2019-12-10-[有点干货]Jdk1.8新特性实战篇41个案例.html'
              },
              {
                title: 'JVM 虚拟机',
                url: '/md/java/interview/2020-11-25-面经手册 · 第19篇《Thread.start() ，它是怎么让线程启动的呢？》.html'
              }
            ]
          },
          {
            title: 'Spring',
            list: [
              {
                title: '手写 Spring',
                url: '/md/spring/develop-spring/2021-05-16-第1章：开篇介绍，手写Spring能给你带来什么？.html'
              },
              {
                title: 'SpringCloud 入门',
                url: '/md/spring/spring-cloud/2019-10-31-Spring Cloud零《总有一偏概述告诉你SpringCloud是什么》.html'
              },
              {
                title: 'Mybatis 源码分析',
                url: '/md/spring/source-code/2019-12-25-2019-12-25-[源码分析]Mybatis接口没有实现类为什么可以执行增删改查.html'
              },
              {
                title: '手写 Mybatis',
                url: '/md/spring/source-code/2020-01-13-2020-01-13-[源码分析]基于jdbc实现一个Demo版的Mybatis.html'
              },
              {
                title: 'Quartz 源码分析',
                url: '/md/spring/source-code/2020-01-01-2020-01-01-[源码解析]Spring定时任务Quartz执行全过程源码解读.html'
              }
            ]
          },
          {
            title: '面向对象',
            list: [
              {
                title: '设计模式',
                url: '/md/develop/design-pattern/2020-05-20-重学Java设计模式《实战工厂方法模式》.html'
              },
              {
                title: 'DDD 落地',
                url: '/md/develop/framework/ddd/2019-10-15-DDD专题案例一《初识领域驱动设计DDD落地》.html'
              },
              {
                title: '低代码',
                url: '/md/develop/framework/scheme/2021-02-21-关于低代码编程的可持续性交付设计和分析.html'
              },
              {
                title: '字节码插桩',
                url: '/md/develop/framework/scheme/2021-02-04-基于IDEA插件开发和字节码插桩技术，实现研发交付质量自动分析.html'
              },
              {
                title: '画架构图',
                url: '/md/develop/framework/scheme/2021-02-28-工作两三年，整不明白架构图都画啥？.html'
              },
              {
                title: '系统监控',
                url: '/md/develop/framework/scheme/2021-07-19-调研字节码插桩技术，用于系统监控设计和实现.html'
              },
              {
                title: '中台',
                url: '/md/develop/framework/scheme/2021-03-24-刚火了的中台转头就拆，一大波公司放不下又拿不起来！.html'
              },
              {
                title: '研发规范',
                url: '/md/develop/standard/2020-09-14-一次代码评审，差点过不了试用期！.html'
              }
            ]
          },
          {
            title: '中间件&插件',
            list: [
              {
                title: 'Maven中央仓库',
                url: '/md/assembly/middleware/2019-12-07-发布Jar包到Maven中央仓库，为开发开源中间件做准备.html'
              },
              {
                title: '数据库路由设计',
                url: '/md/assembly/middleware/2021-08-19-基于Hash散列，数据库路由组件设计.html'
              },
              {
                title: 'IDEA-Plugin',
                url: '/md/assembly/idea-plugin/2021-08-27-技术调研，IDEA%20插件怎么开发？.html'
              },
            ]
          },
          {
            title: 'Netty 4.x',
            list: [
              {
                title: '基础入门',
                url: '/md/netty/base/2019-07-30-netty案例，netty4.1基础入门篇零《初入JavaIO之门BIO、NIO、AIO实战练习》.html'
              },
              {
                title: '中级拓展',
                url: '/md/netty/expand/2019-08-16-netty案例，netty4.1中级拓展篇一《Netty与SpringBoot整合》.html'
              },
              {
                title: '高级应用',
                url: '/md/assembly/idea-plugin/2021-08-27-技术调研，IDEA%20插件怎么开发？.html'
              },
              {
                title: 'RPC 实现',
                url: '/md/netty/application/2019-09-01-手写RPC框架第一章《自定义配置xml》.html'
              },
              {
                title: 'IM 仿微信',
                url: '/md/netty/application/2021-08-17-给学习加点实践，开发一个分布式IM即时通信系统.html'
              },
            ]
          },
          {
            title: '字节码编程',
            list: [
              {
                title: 'ASM',
                url: '/md/bytecode/asm/2020-03-25-[ASM字节码编程]如果你只写CRUD，那这种技术你永远碰不到.html'
              },
              {
                title: 'Javassist',
                url: '/md/bytecode/javassist/2020-04-19-字节码编程，Javassist篇一《基于javassist的第一个案例helloworld》.html'
              },
              {
                title: 'Byte-Buddy',
                url: '/md/bytecode/byte-buddy/2020-05-08-字节码编程，Byte-buddy篇一《基于Byte Buddy语法创建的第一个HelloWorld》.html'
              },
              {
                title: 'JavaAgent',
                url: '/md/bytecode/agent/2019-07-10-基于JavaAgent的全链路监控一《嗨！JavaAgent》.html'
              },
              {
                title: 'ASM-DOC',
                url: '/md/bytecode/asm-document/1引言.html'
              },
              {
                title: 'JVM 指令码',
                url: '/md/bytecode/asm-document/JVM-指令表.html'
              },
            ]
          },
          {
            title: '专栏小册',
            linkMode: 0,
            list: [
              {
                title: '《Netty+JavaFx实战：仿桌面版微信聊天》',
                url: 'https://blog.csdn.net/generalfu/category_10400631.html'
              },
              {
                title: '《SpringBoot 中间件设计和开发》',
                url: 'https://juejin.cn/book/6940996508632219689'
              },
              {
                title: '《Lottery 抽奖系统 - 基于领域驱动设计的四层架构实践》',
                url: 'https://t.zsxq.com/jAi2nUf'
              }
            ]
          },
          {
            title: '知识星球',
            linkMode: 0,
            list: [
              {
                title: '码农会锁',
                url: 'https://t.zsxq.com/jAi2nUf'
              },
              {
                title: '实战项目',
                url: 'https://codechina.csdn.net/KnowledgePlanet'
              }
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

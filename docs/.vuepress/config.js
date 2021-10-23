module.exports = {
    port: "8080",
    dest: ".site",
    base: "/",
    // 是否开启默认预加载js
    shouldPrefetch: (file, type) => {
        return false;
    },
    markdown: {
        lineNumbers: true,
        externalLinks: {
            target: '_blank', rel: 'noopener noreferrer'
        }
    },
    locales: {
        "/": {
            lang: "zh-CN",
            title: "bugstack 虫洞栈",
            description: "包含: Java 基础，面经手册，Netty4.x，手写Spring，用Java实现JVM，重学Java设计模式，SpringBoot中间件开发，IDEA插件开发，DDD系统架构项目开发，字节码编程..."
        }
    },
    head: [
        // ico
        ["link", {rel: "icon", href: `/favicon.ico`}],
        // meta
        ["meta", {name: "robots", content: "all"}],
        ["meta", {name: "author", content: "小傅哥"}],
        ["meta", {
            name: "keywords",
            content: "bugstack 虫洞栈, 重学Java设计模式, 字节码编程, 中间件, Spring, Java基础, 面经手册"
        }],
        ["meta", {name: "apple-mobile-web-app-capable", content: "yes"}],
        ['script',
            {
                charset: 'utf-8',
                async: 'async',
                // src: 'https://code.jquery.com/jquery-3.5.1.min.js',
                src: '/js/jquery.min.js',
            }],
        ['script',
            {
                charset: 'utf-8',
                async: 'async',
                src: 'https://s9.cnzz.com/z_stat.php?id=1278232949&web_id=1278232949',
            }],
        // 添加百度统计
        ["script", {},
            `
              var _hmt = _hmt || [];
              (function() {
                var hm = document.createElement("script");
                hm.src = "https://hm.baidu.com/hm.js?0b31b4c146bf7126aed5009e1a4a11c8";
                var s = document.getElementsByTagName("script")[0];
                s.parentNode.insertBefore(hm, s);
              })();
            `
        ]
    ],
    plugins: [
        [
            {globalUIComponents: ['LockArticle']}
        ],
        // ['@vssue/vuepress-plugin-vssue', {
        //     platform: 'github-v3', //v3的platform是github，v4的是github-v4
        //     // 其他的 Vssue 配置
        //     owner: 'fuzhengwei', //github账户名
        //     repo: 'CodeGuide', //github一个项目的名称
        //     clientId: 'df8beab2190bec20352a',//注册的Client ID
        //     clientSecret: '7eeeb4369d699c933f02a026ae8bb1e2a9c80e90',//注册的Client Secret
        //     autoCreateIssue: true // 自动创建评论，默认是false，最好开启，这样首次进入页面的时候就不用去点击创建评论的按钮了。
        // }
        // ],
        // ['@vuepress/back-to-top', true], replaced with inject page-sidebar
        ['@vuepress/medium-zoom', {
            selector: 'img:not(.nozoom)',
            // See: https://github.com/francoischalifour/medium-zoom#options
            options: {
                margin: 16
            }
        }],
        // see: https://vuepress.github.io/zh/plugins/copyright/#%E5%AE%89%E8%A3%85
        // ['copyright', {
        //     noCopy: false, // 允许复制内容
        //     minLength: 100, // 如果长度超过 100 个字符
        //     authorName: "https://bugstack.cn",
        //     clipboardComponent: "请注明文章出处, [bugstack虫洞栈](https://bugstack.cn)"
        // }],
        // see: https://github.com/ekoeryanto/vuepress-plugin-sitemap
        // ['sitemap', {
        //     hostname: 'https://bugstack.cn'
        // }],
        // see: https://github.com/IOriens/vuepress-plugin-baidu-autopush
        ['vuepress-plugin-baidu-autopush', {}],
        // see: https://github.com/znicholasbrown/vuepress-plugin-code-copy
        ['vuepress-plugin-code-copy', {
            align: 'bottom',
            color: '#3eaf7c',
            successText: '@小傅哥: 代码已经复制到剪贴板'
        }],
        // see: https://github.com/tolking/vuepress-plugin-img-lazy
        ['img-lazy', {}],
        ["vuepress-plugin-tags", {
            type: 'default', // 标签预定义样式
            color: '#42b983',  // 标签字体颜色
            border: '1px solid #e2faef', // 标签边框颜色
            backgroundColor: '#f0faf5', // 标签背景颜色
            selector: '.page .content__default h1' // ^v1.0.1 你要将此标签渲染挂载到哪个元素后面？默认是第一个 H1 标签后面；
        }],
        // https://github.com/lorisleiva/vuepress-plugin-seo
        ["seo", {
            siteTitle: (_, $site) => $site.title,
            title: $page => $page.title,
            description: $page => $page.frontmatter.description,
            author: (_, $site) => $site.themeConfig.author,
            tags: $page => $page.frontmatter.tags,
            // twitterCard: _ => 'summary_large_image',
            type: $page => 'article',
            url: (_, $site, path) => ($site.themeConfig.domain || '') + path,
            image: ($page, $site) => $page.frontmatter.image && (($site.themeConfig.domain && !$page.frontmatter.image.startsWith('http') || '') + $page.frontmatter.image),
            publishedAt: $page => $page.frontmatter.date && new Date($page.frontmatter.date),
            modifiedAt: $page => $page.lastUpdated && new Date($page.lastUpdated),
        }]
    ],
    themeConfig: {
        docsRepo: "fuzhengwei/CodeGuide",
        // 编辑文档的所在目录
        docsDir: 'docs',
        // 文档放在一个特定的分支下：
        docsBranch: 'master',
        //logo: "/logo.png",
        editLinks: true,
        sidebarDepth: 0,
        //smoothScroll: true,
        locales: {
            "/": {
                label: "简体中文",
                selectText: "Languages",
                editLinkText: "在 GitHub 上编辑此页",
                lastUpdated: "上次更新",
                nav: [
                    {
                        text: '导读', link: '/md/other/guide-to-reading.md'
                    },
                    {
                        text: 'Java',
                        items: [
                            {
                                text: '面经手册',
                                link: '/md/java/interview/2020-07-28-面经手册 · 开篇《面试官都问我啥》.md'
                            },
                            {
                                text: '用Java实现JVM',
                                link: '/md/java/develop-jvm/2019-05-01-用Java实现JVM第一章《命令行工具》.md'
                            },
                            {
                                text: '基础技术',
                                link: '/md/java/core/2020-01-06-[源码分析]咋嘞？你的IDEA过期了吧！加个Jar包就破解了，为什么？.md'
                            }
                        ]
                    },
                    {
                        text: 'Spring',
                        items: [
                            {
                                text: 'Spring 手撸专栏',
                                link: '/md/spring/develop-spring/2021-05-16-第1章：开篇介绍，手写Spring能给你带来什么？.md'
                            },
                            {
                                text: 'Spring Cloud',
                                link: '/md/spring/spring-cloud/2019-10-31-Spring Cloud零《总有一偏概述告诉你SpringCloud是什么》.md'
                            },
                            {
                                text: '源码分析(Mybatis、Quartz)',
                                link: '/md/spring/source-code/2019-12-25-[源码分析]Mybatis接口没有实现类为什么可以执行增删改查.md'
                            }
                        ]
                    },
                    {
                        text: '面向对象',
                        items: [
                            {
                                text: '设计模式',
                                items: [
                                    {
                                        text: '创建型模式',
                                        link: '/md/develop/design-pattern/2020-05-20-重学Java设计模式《实战工厂方法模式》.md'
                                    },
                                    {
                                        text: '结构型模式',
                                        link: '/md/develop/design-pattern/2020-06-02-重学 Java 设计模式《适配器模式》.md'
                                    },
                                    {
                                        text: '行为型模式',
                                        link: '/md/develop/design-pattern/2020-06-18-重学 Java 设计模式《实战责任链模式》.md'
                                    }
                                ]
                            },
                            {
                                text: '系统架构',
                                items: [
                                    {
                                        text: 'DDD 专题',
                                        link: '/md/develop/framework/ddd/2019-10-15-DDD专题案例一《初识领域驱动设计DDD落地》.md'
                                    },
                                    {
                                        text: '工程框架',
                                        link: '/md/develop/framework/frame/2019-12-22-架构框架搭建一《单体应用服务之SSM整合：Spring4 + SpringMvc + Mybatis》.md'
                                    },
                                    {
                                        text: '架构方案',
                                        link: '/md/develop/framework/scheme/2021-02-04-基于IDEA插件开发和字节码插桩技术，实现研发交付质量自动分析.md'
                                    }
                                ]
                            },
                            {
                                text: '标准',
                                items: [
                                    {
                                        text: '开发规范&事故',
                                        link: '/md/develop/standard/2020-09-14-一次代码评审，差点过不了试用期！.md'
                                    }
                                ]
                            }
                        ]
                    },
                    {
                        text: '中间件',
                        items: [
                            {
                                text: 'SpringBoot 中间件开发',
                                link: '/md/assembly/middleware/2019-12-02-SpringBoot服务治理中间件之统一白名单验证.md'
                            },
                            {
                                text: 'IDEA-Plugin',
                                link: '/md/assembly/idea-plugin/2021-08-27-技术调研，IDEA 插件怎么开发？.md'
                            }
                        ]
                    },
                    {
                        text: 'Netty 4.x',
                        items: [
                            {
                                text: '基础入门篇',
                                link: '/md/netty/base/2019-07-30-netty案例，netty4.1基础入门篇零《初入JavaIO之门BIO、NIO、AIO实战练习》.md'
                            },
                            {
                                text: '中级拓展篇',
                                link: '/md/netty/expand/2019-08-16-netty案例，netty4.1中级拓展篇一《Netty与SpringBoot整合》.md'
                            },
                            {
                                text: '高级应用篇',
                                link: '/md/netty/application/2019-09-01-手写RPC框架第一章《自定义配置xml》.md'
                            },
                            {
                                text: '源码分析篇',
                                link: '/md/netty/source-code/2019-09-10-netty案例，netty4.1源码分析篇一《NioEventLoopGroup源码分析》.md'
                            },
                        ]
                    },
                    {
                        text: '字节码编程',
                        items: [
                            {
                                text: '框架', items: [
                                    {
                                        text: 'ASM',
                                        link: '/md/bytecode/asm/2020-03-25-[ASM字节码编程]如果你只写CRUD，那这种技术你永远碰不到.md'
                                    },
                                    {
                                        text: 'Javassist',
                                        link: '/md/bytecode/javassist/2020-04-19-字节码编程，Javassist篇一《基于javassist的第一个案例helloworld》.md'
                                    },
                                    {
                                        text: 'Byte-Buddy',
                                        link: '/md/bytecode/byte-buddy/2020-05-08-字节码编程，Byte-buddy篇一《基于Byte Buddy语法创建的第一个HelloWorld》.md'
                                    }
                                ]
                            },
                            {
                                text: '全链路监控', items: [
                                    {
                                        text: 'JavaAgent',
                                        link: '/md/bytecode/agent/2019-07-10-基于JavaAgent的全链路监控一《嗨！JavaAgent》.md'
                                    }
                                ]
                            },
                            {
                                text: '文档', items: [
                                    {text: 'ASM-DOC', link: '/md/bytecode/asm-document/1引言.md'}
                                ]
                            }
                        ]
                    },
                    {
                        text: '专栏|小册|PDF',
                        items: [
                            {
                                text: '我的图书', items: [
                                    {
                                        text: '《重学Java设计模式》',
                                        link: '/md/knowledge/book/2021-04-22-久等了！小傅哥的《重学Java设计模式》终于出版了，彩印&纸质！.md'
                                    }
                                ]
                            },
                            {
                                text: '知识星球', items: [
                                    {
                                        text: '码农会锁',
                                        link: '/md/knowledge/knowledge-planet/2021-08-22-带头撸项目，《DDD + RPC 开发分布式架构，抽奖系统》.md'
                                    }
                                ]
                            },
                            {
                                text: '小册', items: [
                                    {
                                        text: 'Netty+JavaFx实战：仿桌面版微信聊天',
                                        link: '/md/knowledge/booklet/2020-03-04-《Netty+JavaFx实战：仿桌面版微信聊天》.md'
                                    },
                                    {
                                        text: 'SpringBoot 中间件设计和开发',
                                        link: '/md/knowledge/booklet/2021-03-31-《SpringBoot 中间件设计和开发》专栏小册上线啦！.md'
                                    }
                                ]
                            },
                            {
                                text: 'PDF', items: [
                                    {
                                        text: 'Java 面经手册',
                                        link: '/md/knowledge/pdf/2021-01-26-Java面经手册PDF下载.md'
                                    },
                                    {
                                        text: '手撸 Spring',
                                        link: '/md/knowledge/pdf/2021-08-12-《手撸 Spring》PDF，全书260页6.5万字，完稿&发版！.md'
                                    },
                                    {
                                        text: '重学Java设计模式',
                                        link: '/md/knowledge/pdf/2020-07-12-重学 Java 设计模式.md'
                                    },
                                    {
                                        text: '字节码编程',
                                        link: '/md/knowledge/pdf/2020-05-17-小傅哥出书了《字节码编程》免费拿！.md'
                                    }
                                ]
                            }
                        ]
                    },
                    {
                        text: '部署',
                        link: '/md/devops/2019-08-12-windows环境下安装elasticsearch6.2.2.md'
                    },
                    {
                        text: '关于',
                        items: [
                            {text: '关于自己', link: '/md/about/me/about-me.md'},
                            {text: '关于学习', link: '/md/about/study/2020-04-30-讲道理，只要你是一个爱折腾的程序员，毕业找工作真的不需要再花钱培训.md'},
                            {text: '关于职场', link: '/md/about/job/2020-04-11-工作两年简历写成这样，谁要你呀！.md'}
                        ]
                    },
                    {
                        text: 'Github',
                        link: 'https://github.com/fuzhengwei/CodeGuide'
                    }
                ],
                sidebar: {
                    "/md/other/": genBarOther(),
                    "/md/java/interview/": genBarJavaInterview(),
                    "/md/java/develop-jvm/": genBarJavaDevelopJvm(),
                    "/md/java/core/": genBarJavaCore(),
                    "/md/spring/develop-spring/": genBarSpringDevelopSpring(),
                    "/md/spring/source-code/": genBarSpringSourceCode(),
                    "/md/spring/spring-cloud/": genBarSpringSpringCloud(),
                    "/md/develop/design-pattern/": genBarDevelopDesignPattern(),
                    "/md/develop/framework/": genBarDevelopFramework(),
                    "/md/develop/standard/": genBarDevelopStandard(),
                    "/md/devops/": genBarDevOPS(),
                    "/md/assembly/": genBarAssembly(),
                    "/md/netty/": genBarNetty(),
                    "/md/knowledge/": genBarKnowledge(),
                    "/md/bytecode/asm-document/": genBarBytecode(),
                    "/md/bytecode/agent/": genBarBytecodeAgent(),
                    "/md/bytecode/": genBarBytecodeAsmJavassistByteBuddy(),
                    "/md/about/": genBarAbout()
                }
            }
        }
    }
};

// java-interview
function genBarJavaInterview() {
    return [
        {
            title: "第 1 章 谈谈面试",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2020-07-28-面经手册 · 开篇《面试官都问我啥》.md",
                "2020-07-30-面经手册 · 第1篇《认知自己的技术栈盲区》.md",
                "2021-03-07-面试现场：小伙伴美团一面的分享和分析[含解答].md"
            ]
        },
        {
            title: "第 2 章 数据结构和算法",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2020-08-04-面经手册 · 第2篇《数据结构，HashCode为什么使用31作为乘数？》.md",
                "2020-08-07-面经手册 · 第3篇《HashMap核心知识，扰动函数、负载因子、扩容链表拆分，深度学习》.md",
                "2020-08-13-面经手册 · 第4篇《HashMap数据插入、查找、删除、遍历，源码分析》.md",
                "2020-08-16-面经手册 · 第5篇《看图说话，讲解2-3平衡树「红黑树的前身」》.md",
                "2020-08-20-面经手册 · 第6篇《带着面试题学习红黑树操作原理，解析什么时候染色、怎么进行旋转、与2-3树有什么关联》.md",
                "2020-08-27-面经手册 · 第7篇《ArrayList也这么多知识？一个指定位置插入就把谢飞机面晕了！》.md",
                "2020-08-30-面经手册 · 第8篇《LinkedList插入速度比ArrayList快？你确定吗？》.md",
                "2020-09-03-面经手册 · 第9篇《队列是什么？什么是双端队列、延迟对列、阻塞队列，全是知识盲区！》.md",
                "2020-09-10-面经手册 · 第10篇《扫盲java.util.Collections工具包，学习排序、二分、洗牌、旋转算法》.md",
                "2020-09-17-面经手册 · 第11篇《StringBuilder 比 String 快？空嘴白牙的，证据呢！》.md",
                "2020-09-23-面经手册 · 第12篇《面试官，ThreadLocal 你要这么问，我就挂了！》.md"
            ]
        },
        {
            title: "第 3 章 并发和锁",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2020-10-14-面经手册 · 第13篇《除了JDK、CGLIB，还有3种类代理方式？面试又卡住！》.md",
                "2020-10-21-面经手册 · 第14篇《volatile 怎么实现的内存可见？没有 volatile 一定不可见吗？》.md",
                "2020-10-28-面经手册 · 第15篇《码农会锁，synchronized 解毒，剖析源码深度分析！》.md",
                "2020-11-04-面经手册 · 第16篇《码农会锁，ReentrantLock之公平锁讲解和实现》.md",
                "2020-11-11-面经手册 · 第17篇《码农会锁，ReentrantLock之AQS原理分析和实践使用》.md",
                "2020-11-18-面经手册 · 第18篇《AQS 共享锁，Semaphore、CountDownLatch，听说数据库连接池可以用到！》.md"
            ]
        },
        {
            title: "第 4 章 多线程",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2020-11-25-面经手册 · 第19篇《Thread.start() ，它是怎么让线程启动的呢？》.md",
                "2020-12-02-面经手册 · 第20篇《Thread 线程，状态转换、方法使用、原理分析》.md",
                "2020-12-09-面经手册 · 第21篇《手写线程池，对照学习ThreadPoolExecutor线程池实现原理！》.md",
                "2020-12-16-面经手册 · 第22篇《线程池的介绍和使用，以及基于jvmti设计非入侵监控》.md"
            ]
        },
        {
            title: "第 5 章 JVM 虚拟机",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2020-12-23-面经手册 · 第23篇《JDK、JRE、JVM，是什么关系？》.md",
                "2020-12-30-面经手册 · 第24篇《为了搞清楚类加载，竟然手撸JVM！》.md",
                "2021-01-06-面经手册 · 第25篇《JVM内存模型总结，有各版本JDK对比、有元空间OOM监控案例、有Java版虚拟机，综合学习更容易！》.md",
                "2021-01-13-面经手册 · 第26篇《JVM故障处理工具，使用总结》.md",
                "2021-01-20-面经手册 · 第27篇《JVM 判断对象已死，实践验证GC回收》.md",
            ]
        },
        {
            title: "第 6 章 Spring",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2021-03-30-面经手册 · 第28篇《你说，怎么把Bean塞到Spring容器》.md",
                "2021-04-07-面经手册 · 第29篇《Spring IOC 特性有哪些，不会读不懂源码！》.md",
                "2021-04-18-面经手册 · 第30篇《关于 Spring 中 getBean 的全流程源码解析》.md",
                "2021-05-05-面经手册 · 第31篇《Spring Bean IOC、AOP 循环依赖解读》.md",
            ]
        }
    ]
}

// java-develop-jvm
function genBarJavaDevelopJvm() {
    return [
        {
            title: "用Java实现JVM",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2019-05-01-用Java实现JVM第一章《命令行工具》.md",
                "2019-05-02-用Java实现JVM第二章《搜索class文件》.md",
                "2019-05-03-用Java实现JVM第三章《解析class文件》.md",
                "2019-05-04-用Java实现JVM第三章《解析class文件》附[classReader拆解].md",
                "2019-05-05-用Java实现JVM第四章《运行时数据区》.md",
                "2019-05-06-用Java实现JVM第五章《指令集和解释器》.md",
                "2019-05-07-用Java实现JVM第六章《类和对象》.md",
                "2019-05-08-用Java实现JVM第七章《方法调用和返回》.md",
                "2019-05-09-用Java实现JVM第八章《数组和字符串》.md",
                "2019-05-10-用Java实现JVM第九章《本地方法调用》.md",
                "2019-05-11-用Java实现JVM第十章《异常处理》.md"
            ]
        }
    ]
}

// java-core
function genBarJavaCore() {
    return [
        {
            title: "基础技术",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2019-12-10-[有点干货]Jdk1.8新特性实战篇41个案例.md",
                "2019-12-21-[有点干货]JDK、CGLIB动态代理使用以及源码分析.md",
                "2020-01-06-[源码分析]咋嘞？你的IDEA过期了吧！加个Jar包就破解了，为什么？.md",
                "2020-01-18-似乎你总也记不住，byte的取值范围是 -127~128 还是 -128~127.md",
                "2020-03-07-这种场景你还写ifelse你跟孩子坐一桌去吧.md",
                "2020-05-05-汉字不能编程？别闹了，只是看着有点豪横！容易被开除！.md",
                "2020-11-22-鹿鼎记 · 韦小宝，丽春院、天地会、入皇宫等五个场景，搭配不同剧情讲解多线程和锁，真香！.md",
                "2021-04-21-一个Bug，让我发现了 Java 界的AJ锥！.md"
            ]
        }
    ]
}

// other
function genBarOther() {
    return [
        {
            title: "阅读指南",
            collapsable: false,
            sidebarDepth: 2,
            children: [
                "guide-to-reading.md"
            ]
        }
    ]
}

// spring-develop-spring
function genBarSpringDevelopSpring() {
    return [
        {
            title: "容器篇：IOC",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2021-05-16-第1章：开篇介绍，手写Spring能给你带来什么？.md",
                "2021-05-20-第2章：小试牛刀，实现一个简单的Bean容器.md",
                "2021-05-23-第3章：初显身手，运用设计模式，实现 Bean 的定义、注册、获取.md",
                "2021-05-30-第4章：崭露头角，基于Cglib实现含构造函数的类实例化策略.md",
                "2021-06-02-第5章：一鸣惊人，为Bean对象注入属性和依赖Bean的功能实现.md",
                "2021-06-09-第6章：气吞山河，设计与实现资源加载器，从Spring.xml解析和注册Bean对象.md",
                "2021-06-17-第7章：所向披靡，实现应用上下文，自动识别、资源加载、扩展机制.md",
                "2021-06-23-第8章：龙行有风，向虚拟机注册钩子，实现Bean对象的初始化和销毁方法.md",
                "2021-06-28-第9章：虎行有雨，定义标记类型Aware接口，实现感知容器对象.md",
                "2021-06-30-第10章：横刀跃马，关于Bean对象作用域以及FactoryBean的实现和使用.md",
                "2021-07-07-第11章：更上层楼，基于观察者实现，容器事件和事件监听器.md",
            ]
        },
        {
            title: "代理篇：AOP",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2021-07-13-第12章：炉火纯青，基于JDK和Cglib动态代理，实现AOP核心功能.md",
                "2021-07-22-第13章：行云流水，把AOP动态代理，融入到Bean的生命周期.md",
                "2021-07-27-第14章：笑傲江湖，通过注解配置和包自动扫描的方式完成Bean对象的注册.md",
                "2021-08-03-第15章：万人之敌，通过注解给属性注入配置和Bean对象.md",
                "2021-08-05-第16章：战无不胜，给代理对象的属性设置值.md"
            ]
        },
        {
            title: "高级篇：Design",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2021-08-07-第17章：攻无不克，通过三级缓存解决循环依赖.md",
                "2021-08-09-第18章：挂印封刀，数据类型转换工厂设计实现.md",
            ]
        }
    ]
}

// spring-spring-cloud
function genBarSpringSpringCloud() {
    return [
        {
            title: "Spring Cloud",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2019-10-31-Spring Cloud零《总有一偏概述告诉你SpringCloud是什么》.md",
                "2019-11-01-Spring Cloud一《服务集群注册与发现 Eureka》.md",
                "2019-11-02-Spring Cloud二《服务提供与负载均衡调用 Eureka》.md",
                "2019-11-03-Spring Cloud三《应用服务快速失败熔断降级保护 Hystrix》.md",
                "2019-11-04-Spring Cloud四《服务响应性能成功率监控 Hystrix》.md",
                "2019-11-05-Spring Cloud五《Turbine 监控信息聚合展示 Hystrix》.md",
                "2019-11-06-Spring Cloud六《基于Github Webhook动态刷新服务配置》.md",
                "2019-11-07-Spring Cloud七《基于RabbitMQ消息总线方式刷新配置服务》.md",
                "2019-11-08-Spring Cloud八《服务网关路由 Zuul1》.md",
                "2019-11-24-Spring Cloud九《服务网关Zuul 动态路由与权限过滤器》.md"
            ]
        }
    ]
}

// spring-source-code
function genBarSpringSourceCode() {
    return [
        {
            title: "Spring 手撸专栏",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2019-12-25-[源码分析]Mybatis接口没有实现类为什么可以执行增删改查.md",
                "2020-01-01-[源码解析]Spring定时任务Quartz执行全过程源码解读.md",
                "2020-01-08-[源码分析]像盗墓一样分析Spring是怎么初始化xml并注册bean的.md",
                "2020-01-13-[源码分析]基于jdbc实现一个Demo版的Mybatis.md",
                "2020-01-20-[源码分析]手写mybait-spring核心功能，干货好文一次学会工厂bean、类代理、bean注册的使用.md"
            ]
        }
    ]
}

// develop design-pattern
function genBarDevelopDesignPattern() {
    return [
        {
            title: "创建型模式",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2020-05-20-重学Java设计模式《实战工厂方法模式》.md",
                "2020-05-24-重学Java设计模式《抽象工厂模式》.md",
                "2020-05-26-重学Java设计模式《实战建造者模式》.md",
                "2020-05-28-重学 Java 设计模式《实战原型模式》.md",
                "2020-05-31-重学 Java 设计模式《实战单例模式》.md"
            ]
        },
        {
            title: "结构型模式",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2020-06-02-重学 Java 设计模式《适配器模式》.md",
                "2020-06-04-重学 Java 设计模式《实战桥接模式》.md",
                "2020-06-08-重学 Java 设计模式《实战组合模式》.md",
                "2020-06-09-重学 Java 设计模式《实战装饰器模式》.md",
                "2020-06-11-重学 Java 设计模式《实战外观模式》.md",
                "2020-06-14-重学 Java 设计模式《实战享元模式》.md",
                "2020-06-16-重学 Java 设计模式《实战代理模式》.md"
            ]
        },
        {
            title: "行为型模式",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2020-06-18-重学 Java 设计模式《实战责任链模式》.md",
                "2020-06-21-重学 Java 设计模式《实战命令模式》.md",
                "2020-06-23-重学 Java 设计模式《实战迭代器模式》.md",
                "2020-06-27-重学 Java 设计模式《实战中介者模式》.md",
                "2020-06-28-重学 Java 设计模式《实战备忘录模式》.md",
                "2020-06-30-重学 Java 设计模式《实战观察者模式》.md",
                "2020-07-02-重学 Java 设计模式《实战状态模式》.md",
                "2020-07-05-重学 Java 设计模式《实战策略模式》.md",
                "2020-07-07-重学 Java 设计模式《实战模板模式》.md",
                "2020-07-09-重学 Java 设计模式《实战访问者模式》.md"
            ]
        }
    ]
}

// devops
function genBarDevOPS() {
    return [
        {
            title: "SpringBoot 中间件开发",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2019-08-12-windows环境下安装elasticsearch6.2.2.md",
                "2019-08-13-elasticsearch-head插件安装.md",
                "2019-11-23-并不想吹牛皮，但！为了把Github博客粉丝转移到公众号，我干了！.md",
                "2020-03-28-GithubAndMyBlogAttacked.md",
                "2020-04-25-《Netty+JavaFx实战：仿桌面版微信聊天》代码开源+上云部署+视频讲解.md",
                "2020-05-10-自建云盘存储PDF书籍支持在线预览和下载.md",
                "2021-01-24-一天建4个，小傅哥教你搭博客！.md",
                "2021-04-11-Cloudreve 自建云盘实践，我说了没人能限得了我的容量和速度！.md",
                "2021-06-04-【经验分享】码农使用云服务学习，部环境、开端口、配域名、弄SSL、搭博客！.md"
            ]
        }
    ]
}

// develop ddd\frame\framework
function genBarDevelopFramework() {
    return [
        {
            title: "DDD 专题",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "ddd/2019-10-15-DDD专题案例一《初识领域驱动设计DDD落地》.md",
                "ddd/2019-10-16-DDD专题案例二《领域层决策规则树服务设计》.md",
                "ddd/2019-10-17-DDD专题案例三《领域驱动设计架构基于SpringCloud搭建微服务》.md"
            ]
        },
        {
            title: "工程框架",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "frame/2019-12-22-架构框架搭建一《单体应用服务之SSM整合：Spring4 + SpringMvc + Mybatis》.md",
                "frame/2019-12-31-架构框架搭建二《Dubbo分布式领域驱动设计架构框体》.md"
            ]
        },
        {
            title: "架构设计",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "scheme/2021-02-04-基于IDEA插件开发和字节码插桩技术，实现研发交付质量自动分析.md",
                "scheme/2021-02-21-关于低代码编程的可持续性交付设计和分析.md",
                "scheme/2021-02-28-工作两三年，整不明白架构图都画啥？.md",
                "scheme/2021-03-04-笔记整理：技术架构涵盖内容和演变过程总结.md",
                "scheme/2021-03-14-不重复造轮子只是骗小孩子的，教你手撸 SpringBoot 脚手架！.md",
                "scheme/2021-03-24-刚火了的中台转头就拆，一大波公司放不下又拿不起来！.md",
                "scheme/2021-07-19-调研字节码插桩技术，用于系统监控设计和实现.md"
            ]
        }
    ]
}

// develop standard
function genBarDevelopStandard() {
    return [
        {
            title: "研发标准&事故",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2020-09-06-握草，你竟然在代码里下毒！.md",
                "2020-09-14-一次代码评审，差点过不了试用期！.md",
                "2021-01-03-谁说明天上线，这货压根不知道开发流程！.md",
                // "2021-01-10-握草，这些研发事故30%我都干过！.md",
                "2021-09-15-还重构？就你那代码只能铲了重写！.md",
                "2021-09-27-p3c 插件，是怎么检查出你那屎山的代码？.md",
                "2021-10-10-12种 vo2dto 方法，就 BeanUtils.copyProperties 压测最拉胯.md"
            ]
        }
    ]
}

// Assembly
function genBarAssembly() {
    return [
        {
            title: "SpringBoot 中间件开发",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "middleware/2019-12-02-SpringBoot服务治理中间件之统一白名单验证.md",
                "middleware/2019-12-07-发布Jar包到Maven中央仓库，为开发开源中间件做准备.md",
                "middleware/2019-12-08-开发基于SpringBoot的分布式任务中间件DcsSchedule.md",
                "middleware/2021-08-19-基于Hash散列，数据库路由组件设计.md"
            ]
        },
        {
            title: "IDEA-Plugin",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "idea-plugin/2021-08-27-技术调研，IDEA 插件怎么开发？.md"
            ]
        }
    ]
}

// netty 4.x
function genBarNetty() {
    return [
        {
            title: "基础入门篇",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "base/2019-07-30-netty案例，netty4.1基础入门篇零《初入JavaIO之门BIO、NIO、AIO实战练习》.md",
                "base/2019-08-01-netty案例，netty4.1基础入门篇一《嗨！NettyServer》.md",
                "base/2019-08-05-netty案例，netty4.1基础入门篇二《NettyServer接收数据》.md",
                "base/2019-08-06-netty案例，netty4.1基础入门篇三《NettyServer字符串解码器》.md",
                "base/2019-08-07-netty案例，netty4.1基础入门篇四《NettyServer收发数据》.md",
                "base/2019-08-08-netty案例，netty4.1基础入门篇五《NettyServer字符串编码器》.md",
                "base/2019-08-09-netty案例，netty4.1基础入门篇六《NettyServer群发消息》.md",
                "base/2019-08-10-netty案例，netty4.1基础入门篇七《嗨！NettyClient》.md",
                "base/2019-08-11-netty案例，netty4.1基础入门篇八《NettyClient半包粘包处理、编码解码处理、收发数据方式》.md",
                "base/2019-08-12-netty案例，netty4.1基础入门篇九《自定义编码解码器，处理半包、粘包数据》.md",
                "base/2019-08-13-netty案例，netty4.1基础入门篇十《关于ChannelOutboundHandlerAdapter简单使用》.md",
                "base/2019-08-14-netty案例，netty4.1基础入门篇十一《netty udp通信方式案例Demo》.md",
                "base/2019-08-15-netty案例，netty4.1基础入门篇十二《简单实现一个基于Netty搭建的Http服务》.md"
            ]
        },
        {
            title: "中级拓展篇",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "expand/2019-08-16-netty案例，netty4.1中级拓展篇一《Netty与SpringBoot整合》.md",
                "expand/2019-08-17-netty案例，netty4.1中级拓展篇二《Netty使用Protobuf传输数据》.md",
                "expand/2019-08-18-netty案例，netty4.1中级拓展篇三《Netty传输Java对象》.md",
                "expand/2019-08-19-netty案例，netty4.1中级拓展篇四《Netty传输文件、分片发送、断点续传》.md",
                "expand/2019-08-20-netty案例，netty4.1中级拓展篇五《基于Netty搭建WebSocket，模仿微信聊天页面》.md",
                "expand/2019-08-21-netty案例，netty4.1中级拓展篇六《SpringBoot+Netty+Elasticsearch收集日志信息数据存储》.md",
                "expand/2019-08-22-netty案例，netty4.1中级拓展篇七《Netty请求响应同步通信》.md",
                "expand/2019-08-23-netty案例，netty4.1中级拓展篇八《Netty心跳服务与断线重连》.md",
                "expand/2019-08-24-netty案例，netty4.1中级拓展篇九《Netty集群部署实现跨服务端通信的落地方案》.md",
                "expand/2019-08-25-netty案例，netty4.1中级拓展篇十《Netty接收发送多种协议消息类型的通信处理方案》.md",
                "expand/2019-08-26-netty案例，netty4.1中级拓展篇十一《Netty基于ChunkedStream数据流切块传输》.md",
                "expand/2019-08-27-netty案例，netty4.1中级拓展篇十二《Netty流量整形数据流速率控制分析与实战》.md",
                "expand/2019-08-28-netty案例，netty4.1中级拓展篇十三《Netty基于SSL实现信息传输过程中双向加密验证》.md"
            ]
        },
        {
            title: "高级应用篇",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "application/2019-09-01-手写RPC框架第一章《自定义配置xml》.md",
                "application/2019-09-02-手写RPC框架第二章《netty通信》.md",
                "application/2019-09-03-手写RPC框架第三章《RPC中间件》.md",
                "application/2019-12-01-websocket与下位机通过netty方式通信传输行为信息.md",
                "application/2021-08-17-给学习加点实践，开发一个分布式IM即时通信系统.md",
            ]
        },
        {
            title: "源码分析篇",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "source-code/2019-09-10-netty案例，netty4.1源码分析篇一《NioEventLoopGroup源码分析》.md",
                "source-code/2019-09-11-netty案例，netty4.1源码分析篇二《ServerBootstrap配置与绑定启动》.md",
                "source-code/2019-09-12-netty案例，netty4.1源码分析篇三《Netty服务端初始化过程以及反射工厂的作用》.md",
                "source-code/2019-09-13-netty案例，netty4.1源码分析篇四《ByteBuf的数据结构在使用方式中的剖析》.md",
                "source-code/2019-09-14-netty案例，netty4.1源码分析篇五《一行简单的writeAndFlush都做了哪些事》.md",
                "source-code/2019-09-15-netty案例，netty4.1源码分析篇六《Netty异步架构监听类Promise源码分析》.md",
            ]
        }
    ]
}

// knowledge
function genBarKnowledge() {
    return [
        {
            title: "介绍",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "knowledge.md",
            ]
        },
        {
            title: "我的图书",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "book/2021-04-22-久等了！小傅哥的《重学Java设计模式》终于出版了，彩印&纸质！.md",
            ]
        },
        {
            title: "知识星球",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "knowledge-planet/2021-08-22-带头撸项目，《DDD + RPC 开发分布式架构，抽奖系统》.md",
            ]
        },
        {
            title: "小册",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "booklet/2020-03-04-《Netty+JavaFx实战：仿桌面版微信聊天》.md",
                "booklet/2021-03-31-《SpringBoot 中间件设计和开发》专栏小册上线啦！.md"
            ]
        },
        {
            title: "PDF",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "pdf/2020-05-17-小傅哥出书了《字节码编程》免费拿！.md",
                "pdf/2020-07-12-重学 Java 设计模式.md",
                "pdf/2021-01-26-Java面经手册PDF下载.md",
                "pdf/2021-08-12-《手撸 Spring》PDF，全书260页6.5万字，完稿&发版！.md",
            ]
        }
    ]
}

function genBarBytecodeAsmJavassistByteBuddy() {
    return [
        {
            title: "ASM",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "asm/2020-03-25-[ASM字节码编程]如果你只写CRUD，那这种技术你永远碰不到.md",
                "asm/2020-04-05-[ASM字节码编程]JavaAgent+ASM字节码插桩采集方法名称以及入参和出参结果并记录方法耗时.md",
                "asm/2020-04-16-[ASM字节码编程]用字节码增强技术给所有方法加上TryCatch捕获异常并输出.md"
            ]
        },
        {
            title: "Javassist",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "javassist/2020-04-19-字节码编程，Javassist篇一《基于javassist的第一个案例helloworld》.md",
                "javassist/2020-04-20-字节码编程，Javassist篇二《定义属性以及创建方法时多种入参和出参类型的使用》.md",
                "javassist/2020-04-21-字节码编程，Javassist篇三《使用Javassist在运行时重新加载类「替换原方法输出不一样的结果」》.md",
                "javassist/2020-04-27-字节码编程，Javassist篇四《通过字节码插桩监控方法采集运行时入参出参和异常信息》.md",
                "javassist/2020-04-29-字节码编程，Javassist篇五《使用Bytecode指令码生成含有自定义注解的类和方法》.md",
            ]
        },
        {
            title: "Byte-Buddy",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "byte-buddy/2020-05-08-字节码编程，Byte-buddy篇一《基于Byte Buddy语法创建的第一个HelloWorld》.md",
                "byte-buddy/2020-05-12-字节码编程，Byte-buddy篇二《监控方法执行耗时动态获取出入参类型和值》.md",
                "byte-buddy/2020-05-14-字节码编程，Byte-buddy篇三《使用委托实现抽象类方法并注入自定义注解信息》.md",
            ]
        }
    ]
}

function genBarBytecodeAgent() {
    return [
        {
            title: "JavaAgent",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2019-07-10-基于JavaAgent的全链路监控一《嗨！JavaAgent》.md",
                "2019-07-11-基于JavaAgent的全链路监控二《通过字节码增加监控执行耗时》.md",
                "2019-07-12-基于JavaAgent的全链路监控三《ByteBuddy操作监控方法字节码》.md",
                "2019-07-13-基于JavaAgent的全链路监控四《JVM内存与GC信息》.md",
                "2019-07-14-基于JavaAgent的全链路监控五《ThreadLocal链路追踪》.md",
                "2019-07-15-基于JavaAgent的全链路监控六《开发应用级监控》.md",
            ]
        }
    ]
}

// bytecode-asm-document
function genBarBytecode() {
    return [
        {
            title: "第 1 章 - 引言",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "1引言.md",
            ]
        },
        {
            title: "第 2 章 - 类",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "2.1结构.md",
                "2.2接口和组件.md",
                "2.3工具.md"
            ]
        },
        {
            title: "第 3 章 - 方法",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "3.1结构.md",
                "3.2接口和组件.md",
                "3.3工具.md"
            ]
        },
        {
            title: "第 4 章 - 元数据",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "4.1泛型.md",
                "4.2注释.md",
                "4.3调试.md"
            ]
        },
        {
            title: "第 5 章 - 后向兼容",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "5.1引言.md",
                "5.2规则.md",
            ]
        },
        {
            title: "第 6 章 - 类",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "6.1接口和组件.md",
                "6.2组件合成.md",
            ]
        },
        {
            title: "第 7 章 - 方法",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "7.1接口和组件.md",
                "7.2组件合成.md",
            ]
        },
        {
            title: "第 8 章 - 方法分析",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "8.1介绍.md",
                "8.2组件与接口.md"
            ]
        },
        {
            title: "第 9 章 - 元数据",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "9.1泛型.md",
                "9.2注释.md",
                "9.3调试.md"
            ]
        },
        {
            title: "第 10 章 - 后向兼容",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "10.1介绍.md",
                "10.2规则.md"
            ]
        },
        {
            title: "A. 附录",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "A.1字节代码指.md",
                "A.2子例程.md",
                "A.3属性.md",
                "A.4规则.md",
                "A.5性能.md",
                "JVM-指令表.md"
            ]
        }
    ];
}

// About page
function genBarAbout() {
    return [
        {
            title: "关于自己",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "me/about-me.md",
                "me/2020-03-31-大学四年到毕业工作5年的学习路线资源汇总.md",
                "me/2020-07-25-12天，这本《重学Java设计模式》PDF书籍下载量9k，新增粉丝1400人，Github上全球推荐榜.md",
                "me/2020-08-25-13年毕业，用两年时间从外包走进互联网大厂.md",
                "me/2020-10-09-让人怪不好意思的，粉丝破万，用了1年！.md",
                "me/2020-10-25-今天你写博客了吗.md",
                "me/2020-11-01-刚毕业不久，接私活赚了2万块！.md",
                "me/2020-11-29-北漂码农的我，把在大城市过成了屯子一样舒服，哈哈哈哈哈！.md",
                "me/2020-12-27-2020总结 | 作为技术号主的一年！.md",
                "me/2021-01-31-这一年，想踏码进货一样！.md",
                "me/2021-05-26-小傅哥，一个有副业的码农.md",
                "me/2021-06-20-我，有10万+粉丝啦！.md",
                "me/2021-07-03-以一己之力，生抗美团技术博客！.md",
                "me/2021-09-05-我在CSDN赚了1.2万.md",
            ]
        },
        {
            title: "关于学习",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "study/2020-04-30-讲道理，只要你是一个爱折腾的程序员，毕业找工作真的不需要再花钱培训.md",
                "study/2020-10-11-为了省钱，我用1天时间把PHP学了！.md",
                "study/2020-10-18-UML类图还不懂？来看看这版乡村爱情类图，一把学会！.md",
                "study/2020-11-08-一个简单的能力，决定你是否会学习！.md",
                "study/2020-12-06-90Per的程序员，都没用过多线程和锁，怎么成为架构师？.md",
                "study/2020-12-13-码德需求，这不就是产品给我留的数学作业！.md",
                "study/2020-12-20-工作3年，看啥资料能月薪30K？.md",
                "study/2021-01-17-数学，离一个程序员有多近？.md",
                "study/2021-05-09-大学毕业要写多少行代码，才能不用花钱培训就找到一份开发工作？.md"
            ]
        },
        {
            title: "关于职场",
            collapsable: false,
            sidebarDepth: 0,
            children: [
                "job/2020-04-11-工作两年简历写成这样，谁要你呀！.md",
                "job/2020-09-20-程序员为什么热衷于造轮子，升职加薪吗？.md",
                "job/2020-09-27-PPT画成这样，述职答辩还能过吗？.md",
                "job/2020-11-15-BATJTMD，大厂招聘，都招什么样Java程序员？.md",
                "job/2020-12-20-工作3年，看啥资料能月薪30K？.md",
                "job/2021-02-24-半年筛选了400+份简历，告诉你怎么写会被撩.md"
            ]
        }
    ];
}


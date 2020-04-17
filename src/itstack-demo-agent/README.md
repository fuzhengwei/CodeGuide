# itstack-demo-agent 基于JavaAgent的全链路监控
>目前市面的全链路监控系统基本都是参考Google的Dapper来做的，本专题主要通过六个章节的代码实战，来介绍如何使用javaagent以及字节码应用，来实现一个简单的java代码链路流程监控。  

## 章节列表  
- [基于JavaAgent的全链路监控一《嗨！JavaAgent》](https://bugstack.cn/itstack-demo-agent/2019/07/10/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%B8%80-%E5%97%A8-JavaAgent.html)
- [基于JavaAgent的全链路监控二《通过字节码增加监控执行耗时》](https://bugstack.cn/itstack-demo-agent/2019/07/11/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%BA%8C-%E9%80%9A%E8%BF%87%E5%AD%97%E8%8A%82%E7%A0%81%E5%A2%9E%E5%8A%A0%E7%9B%91%E6%8E%A7%E6%89%A7%E8%A1%8C%E8%80%97%E6%97%B6.html)
- [基于JavaAgent的全链路监控三《ByteBuddy操作监控方法字节码》](https://bugstack.cn/itstack-demo-agent/2019/07/12/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%B8%89-ByteBuddy%E6%93%8D%E4%BD%9C%E7%9B%91%E6%8E%A7%E6%96%B9%E6%B3%95%E5%AD%97%E8%8A%82%E7%A0%81.html)
- [基于JavaAgent的全链路监控四《JVM内存与GC信息》](https://bugstack.cn/itstack-demo-agent/2019/07/13/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E5%9B%9B-JVM%E5%86%85%E5%AD%98%E4%B8%8EGC%E4%BF%A1%E6%81%AF.html)
- [基于JavaAgent的全链路监控五《ThreadLocal链路追踪》](https://bugstack.cn/itstack-demo-agent/2019/07/14/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E4%BA%94-ThreadLocal%E9%93%BE%E8%B7%AF%E8%BF%BD%E8%B8%AA.html)
- [基于JavaAgent的全链路监控六《开发应用级监控》](https://bugstack.cn/itstack-demo-agent/2019/07/15/%E5%9F%BA%E4%BA%8EJavaAgent%E7%9A%84%E5%85%A8%E9%93%BE%E8%B7%AF%E7%9B%91%E6%8E%A7%E5%85%AD-%E5%BC%80%E5%8F%91%E5%BA%94%E7%94%A8%E7%BA%A7%E7%9B%91%E6%8E%A7.html)


------------

## 微信公众号：bugstack虫洞栈，欢迎您的关注&获取源码！

![微信公众号：bugstack虫洞栈，欢迎您的关注&获取源码！](https://bugstack.cn/wp-content/uploads/2019/08/qrcode清晰.png)

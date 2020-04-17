package org.itstack.demo.agent.plugin;

import org.itstack.demo.agent.plugin.impl.jvm.JvmPlugin;
import org.itstack.demo.agent.plugin.impl.link.LinkPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * 博客：http://itstack.org
 * 论坛：http://bugstack.cn
 * 公众号：bugstack虫洞栈  ｛获取学习源码｝
 * create by fuzhengwei on 2019
 */
public class PluginFactory {

    public static List<IPlugin> pluginGroup = new ArrayList<>();

    static {
        //链路监控
        pluginGroup.add(new LinkPlugin());
        //Jvm监控
        pluginGroup.add(new JvmPlugin());
    }

}

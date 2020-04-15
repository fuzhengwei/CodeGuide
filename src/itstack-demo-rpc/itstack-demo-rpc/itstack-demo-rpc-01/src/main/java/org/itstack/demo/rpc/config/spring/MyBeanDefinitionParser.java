package org.itstack.demo.rpc.config.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * http://www.itstack.org
 * create by fuzhengwei on 2019/5/6
 */
public class MyBeanDefinitionParser implements BeanDefinitionParser {

    private final Class<?> beanClass;

    MyBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {

        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setLazyInit(false);
        String beanName = element.getAttribute("id");
        parserContext.getRegistry().registerBeanDefinition(beanName, beanDefinition);

        for (Method method : beanClass.getMethods()) {
            if (!isProperty(method, beanClass)) continue;
            String name = method.getName();
            String methodName = name.substring(3, 4).toLowerCase() + name.substring(4);
            String value = element.getAttribute(methodName);
            beanDefinition.getPropertyValues().addPropertyValue(methodName, value);
        }

        return beanDefinition;
    }

    private boolean isProperty(Method method, Class beanClass) {

        String methodName = method.getName();
        boolean flag = methodName.length() > 3 && methodName.startsWith("set") && Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length == 1;
        Method getter = null;
        if (!flag) return false;

        Class<?> type = method.getParameterTypes()[0];
        try {
            getter = beanClass.getMethod("get" + methodName.substring(3));
        } catch (NoSuchMethodException ignore) {

        }

        if (null == getter) {
            try {
                getter = beanClass.getMethod("is" + methodName.substring(3));
            } catch (NoSuchMethodException ignore) {

            }
        }

        flag = getter != null && Modifier.isPublic(getter.getModifiers()) && type.equals(getter.getReturnType());

        return flag;

    }

}

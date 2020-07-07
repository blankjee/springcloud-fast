package org.blankjee.cloudfast.common.util;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @Author blankjee
 * @Date 2020/7/6 17:22
 */
@Component
public final class SpringUtil implements BeanFactoryPostProcessor {

    // Spring 应用上下文
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        SpringUtil.beanFactory = beanFactory;
    }

    /**
     * 获取对象
     * @param name
     * @param <T>
     * @return Object bean实例对象
     * @throws BeansException
     */
    @SuppressWarnings("unchecked")
    public static <T> T genBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     * @param clz
     * @param <T>
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> clz) throws BeansException {
        T result = (T) beanFactory.getBean(clz);
        return result;
    }

    /**
     * 如果BeanFactory包含一个所给名称匹配的Bean对象，则返回true。
     * @param name
     * @return
     */
    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    /**
     * 判断给定名字注册的bean定义是一个singleton还是prototype，如果没有找到则返回异常。
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.isSingleton(name);
    }

    /**
     * 注册对象的类型
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getType(name);
    }

    /**
     * 给定bean名字有别名，则返回别名
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getAliases(name);
    }

    /**
     * 获取AOP代理对象
     * @param invoker
     * @param <T>
     * @return
     */
    public static <T> T getAopProxy(T invoker) {
        return (T) AopContext.currentProxy();
    }
}

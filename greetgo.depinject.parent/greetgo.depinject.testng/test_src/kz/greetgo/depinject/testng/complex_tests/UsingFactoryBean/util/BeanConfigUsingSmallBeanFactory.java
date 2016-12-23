package kz.greetgo.depinject.testng.complex_tests.UsingFactoryBean.util;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.testng.complex_tests.UsingFactoryBean.beans.bean_factory_big.BeanConfigSmallBeanFactory;
import kz.greetgo.depinject.testng.complex_tests.UsingFactoryBean.beans.core.BeanConfigUsingBeanFactory;

@BeanConfig
@Include({BeanConfigUsingBeanFactory.class, BeanConfigSmallBeanFactory.class})
public class BeanConfigUsingSmallBeanFactory {
}

package kz.greetgo.depinject.testng.complex_tests.UsingBeanFactory.util;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.testng.complex_tests.UsingBeanFactory.beans.bean_factory_big.BeanConfigSmallBeanFactory;
import kz.greetgo.depinject.testng.complex_tests.UsingBeanFactory.beans.core.BeanConfigUsingBeanFactory;

@BeanConfig
@Include({BeanConfigUsingBeanFactory.class, BeanConfigSmallBeanFactory.class})
public class BeanConfigUsingSmallBeanFactory {
}

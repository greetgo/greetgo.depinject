package kz.greetgo.depinject.testng.complex_tests.UsingBeanFactory.util;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.testng.complex_tests.UsingBeanFactory.beans.bean_factory_small.BeanConfigBigBeanFactory;
import kz.greetgo.depinject.testng.complex_tests.UsingBeanFactory.beans.core.BeanConfigUsingBeanFactory;

@BeanConfig
@Include({BeanConfigUsingBeanFactory.class, BeanConfigBigBeanFactory.class})
public class BeanConfigUsingBigBeanFactory {
}

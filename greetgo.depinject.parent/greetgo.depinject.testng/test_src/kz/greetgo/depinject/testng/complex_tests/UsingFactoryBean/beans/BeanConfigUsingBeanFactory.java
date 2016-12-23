package kz.greetgo.depinject.testng.complex_tests.UsingFactoryBean.beans;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;

@BeanConfig(defaultFactoryClass = LocalBeanFactory.class)
@BeanScanner
public class BeanConfigUsingBeanFactory {
}

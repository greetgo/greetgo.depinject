package kz.greetgo.depinject.testng.complex_tests.UsingBeanFactory.beans.core;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;


@BeanScanner
@BeanConfig(factory = LocalBeanFactory.class)
public class BeanConfigUsingBeanFactory {
}

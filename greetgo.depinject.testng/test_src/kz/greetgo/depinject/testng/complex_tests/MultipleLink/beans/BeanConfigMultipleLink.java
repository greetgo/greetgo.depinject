package kz.greetgo.depinject.testng.complex_tests.MultipleLink.beans;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;


@BeanScanner
@BeanConfig(defaultFactoryClass = MultipleLinkWindowFactory.class)
public class BeanConfigMultipleLink {
}

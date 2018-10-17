package kz.greetgo.depinject.testng.complex_tests.MultipleLink.beans;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;


@BeanScanner
@BeanConfig(factory = MultipleLinkWindowFactory.class)
public class BeanConfigMultipleLink {
}

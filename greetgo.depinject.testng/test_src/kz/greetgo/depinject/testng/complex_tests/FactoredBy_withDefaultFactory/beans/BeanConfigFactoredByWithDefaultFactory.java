package kz.greetgo.depinject.testng.complex_tests.FactoredBy_withDefaultFactory.beans;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;

@BeanScanner
@BeanConfig(factory = DefaultComputerFactory.class)
public class BeanConfigFactoredByWithDefaultFactory {
}

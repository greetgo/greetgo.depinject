package kz.greetgo.depinject.testng.complex_tests.UsingBeanFactory.beans.core;

import kz.greetgo.depinject.core.Bean;

@Bean(singleton = false)
public interface UsingBeanFactoryComputer {
  void turnOn();
}

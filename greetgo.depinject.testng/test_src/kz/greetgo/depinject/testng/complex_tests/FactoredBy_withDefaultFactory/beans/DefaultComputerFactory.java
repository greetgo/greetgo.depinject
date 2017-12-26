package kz.greetgo.depinject.testng.complex_tests.FactoredBy_withDefaultFactory.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanFactory;

@Bean
public class DefaultComputerFactory implements BeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) throws Exception {
    throw new UnsupportedOperationException();
  }
}

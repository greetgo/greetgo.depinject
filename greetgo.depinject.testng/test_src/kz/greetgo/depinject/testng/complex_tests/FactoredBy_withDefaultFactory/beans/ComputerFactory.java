package kz.greetgo.depinject.testng.complex_tests.FactoredBy_withDefaultFactory.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanFactory;

import static kz.greetgo.depinject.testng.complex_tests.FactoredBy_withDefaultFactory.FactoredBy_withDefaultFactory.log;

@Bean
public class ComputerFactory implements BeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) throws Exception {

    if (beanClass == Computer.class) {
      return (Computer) () -> log.add("Type in computer");
    }

    throw new IllegalArgumentException("Unknown " + beanClass);
  }
}

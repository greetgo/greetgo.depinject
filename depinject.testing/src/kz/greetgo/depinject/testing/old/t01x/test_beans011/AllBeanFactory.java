package kz.greetgo.depinject.testing.old.t01x.test_beans011;

import kz.greetgo.depinject.ann.Bean;

@Bean
public class AllBeanFactory {
  @Bean
  public BeanA3_beanFactory createBeanA3_beanFactory() {
    return new BeanA3_beanFactory();
  }
}

package kz.greetgo.depinject.gen.test_beans010;

import kz.greetgo.depinject.core.Bean;

@Bean
public class AllBeanFactory {
  @Bean
  public BeanA2 createBeanA2() {
    return () -> { };
  }

  @Bean
  public BeanA3_beanFactory createBeanA3_beanFactory() {
    return new BeanA3_beanFactory();
  }
}

package kz.greetgo.depinject.gen2.test_beans004;

import kz.greetgo.depinject.core.Bean;

@Bean
public class BeanFactoryIn004 {

  @Bean
  public SomeBean create(int x) {
    return new SomeBean();
  }

}

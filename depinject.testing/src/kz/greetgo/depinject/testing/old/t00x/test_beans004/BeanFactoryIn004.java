package kz.greetgo.depinject.testing.old.t00x.test_beans004;

import kz.greetgo.depinject.ann.Bean;

@Bean
public class BeanFactoryIn004 {

  @Bean
  public SomeBean create(int x) {
    return new SomeBean();
  }

}

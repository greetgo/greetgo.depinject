package kz.greetgo.depinject.testing.old.t00x.test_beans007;

import kz.greetgo.depinject.ann.Bean;

@Bean
public class SomeBeanFactory {
  @Bean
  public SomeBeanClass create1() {
    return new SomeBeanClass();
  }

  @Bean
  public SomeBeanClass create2() {
    return new SomeBeanClass();
  }
}

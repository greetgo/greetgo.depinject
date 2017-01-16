package kz.greetgo.depinject.gen.test_beans007;

import kz.greetgo.depinject.core.Bean;

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

package kz.greetgo.depinject.testing.old.t01x.test_beans016;

import kz.greetgo.depinject.ann.Bean;

@Bean
public class SomeBeanFactory016 {
  @Bean
  @SuppressWarnings("unused")
  public SomeBean016 createBean1() {
    return new SomeBean016() {
    };
  }

  @Bean
  @SuppressWarnings("unused")
  public SomeBean016 createBean2() {
    return new SomeBean016() {
    };
  }

  @Bean
  @SuppressWarnings("unused")
  public SomeBean016 createBean3() {
    return new SomeBean016() {
    };
  }

}

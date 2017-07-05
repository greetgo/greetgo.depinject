package kz.greetgo.depinject.gen.test_beans003;

import kz.greetgo.depinject.core.Bean;

@Bean
public class BeanFactory {
  @Bean
  public Bean1 createBean1() {
    return new Bean1();
  }

  @Bean(singleton = false)
  public Bean2 createBean2() {
    return new Bean2();
  }

}

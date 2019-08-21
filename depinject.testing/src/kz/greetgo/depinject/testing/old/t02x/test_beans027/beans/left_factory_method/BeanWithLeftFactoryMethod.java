package kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.left_factory_method;

import kz.greetgo.depinject.ann.Bean;

@Bean
public class BeanWithLeftFactoryMethod {

  @Bean
  public String createBean(int x) {
    return null;
  }

}

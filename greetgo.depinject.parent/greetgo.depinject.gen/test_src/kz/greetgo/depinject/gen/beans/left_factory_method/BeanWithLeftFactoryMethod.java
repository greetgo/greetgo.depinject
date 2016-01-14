package kz.greetgo.depinject.gen.beans.left_factory_method;

import kz.greetgo.depinject.core.Bean;

@Bean
public class BeanWithLeftFactoryMethod {

  @Bean
  public String createBean(int x) {
    return null;
  }

}

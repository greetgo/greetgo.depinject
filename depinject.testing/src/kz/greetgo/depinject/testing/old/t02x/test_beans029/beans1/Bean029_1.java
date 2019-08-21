package kz.greetgo.depinject.testing.old.t02x.test_beans029.beans1;

import kz.greetgo.depinject.ann.Bean;

@Bean
public class Bean029_1 {
  @Bean
  public Bean029_1_1 create() {
    return new Bean029_1_1();
  }

}

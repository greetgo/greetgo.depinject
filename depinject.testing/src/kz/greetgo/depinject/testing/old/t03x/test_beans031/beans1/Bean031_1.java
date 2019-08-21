package kz.greetgo.depinject.testing.old.t03x.test_beans031.beans1;

import kz.greetgo.depinject.ann.Bean;

@Bean
public class Bean031_1 {

  @Bean
  public Bean031_1_1 create() {
    return new Bean031_1_1();
  }

}

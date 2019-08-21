package kz.greetgo.depinject.testing.old.t03x.test_beans030.beans1;

import kz.greetgo.depinject.ann.Bean;

@Bean
public class Bean030_1 {
  @Bean
  public Bean030_1_1 create() {
    return new Bean030_1_1();
  }

}

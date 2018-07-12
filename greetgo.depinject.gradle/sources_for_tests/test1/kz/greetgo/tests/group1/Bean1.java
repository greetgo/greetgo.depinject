package kz.greetgo.tests.group1;

import kz.greetgo.depinject.core.Bean;

@Bean
public class Bean1 {
  public void hello() {
    System.out.println("Hello from bean1");
  }
}

package kz.greetgo.kotlin_probe.module2;

import kz.greetgo.depinject.core.Bean;

@Bean
public class Bean2 {
  public void printHelloWorld() {
    System.out.println("Hello World from " + getClass());
  }
}

package kz.greetgo.depinject.testing.beans2;

import kz.greetgo.depinject.ann.Bean;

@Bean
public class Hello2 {
  public void hello() {
    System.out.println("Hello from 2");
  }
}
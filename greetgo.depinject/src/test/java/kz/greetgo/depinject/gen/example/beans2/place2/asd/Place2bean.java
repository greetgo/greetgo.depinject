package kz.greetgo.depinject.gen.example.beans2.place2.asd;

import kz.greetgo.depinject.src.Bean;
import kz.greetgo.depinject.src.HasAfterInject;

@Bean
public class Place2bean implements HasAfterInject {
  
  public void boom() {
    System.out.println("boom");
  }
  
  @Override
  public void afterInject() throws Exception {
    System.out.println("After inject in " + getClass());
  }
}

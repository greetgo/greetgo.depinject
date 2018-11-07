package kz.greetgo.lombok.module2.fruits;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.lombok.module3.fruits.Fruit;

@Bean
public class MyFruits {
  @Bean(id = "green-lemon")
  public Fruit greenLemon() {
    return () -> "green lemon";
  }
}

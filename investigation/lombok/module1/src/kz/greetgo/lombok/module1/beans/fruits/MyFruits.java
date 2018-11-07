package kz.greetgo.lombok.module1.beans.fruits;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.lombok.module3.fruits.Fruit;

@Bean
public class MyFruits {

  @Bean(id = "red-apple")
  public Fruit redApple() {
    return () -> ("red apple");
  }

  @Bean(id = "green-apple")
  public Fruit greenApple() {
    return () -> ("green apple");
  }

  @Bean(id = "green-banana")
  public Fruit greenBanana() {
    return () -> ("green banana");
  }

}

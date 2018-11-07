package kz.greetgo.lombok.module2.fruits;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.lombok.module3.fruits.Fruit;

@Bean(id = "yellow-banana")
public class YellowBanana implements Fruit {
  @Override
  public String name() {
    return "yellow banana";
  }
}

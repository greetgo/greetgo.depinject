package kz.greetgo.lombok.module3.fruits;

import kz.greetgo.depinject.core.Bean;

@Bean(id = "yellow-apple")
public class YellowApple implements Fruit {

  @Override
  public String name() {
    return "yellow apple";
  }
}

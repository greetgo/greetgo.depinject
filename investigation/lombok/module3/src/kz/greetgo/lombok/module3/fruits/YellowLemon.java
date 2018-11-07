package kz.greetgo.lombok.module3.fruits;

import kz.greetgo.depinject.core.Bean;

@Bean(id = "yellow-lemon")
public class YellowLemon implements Fruit {
  @Override
  public String name() {
    return "yellow lemon";
  }
}

package kz.greetgo.lombok.module1.beans.fruits;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.Qualifier;
import kz.greetgo.lombok.module3.fruits.Fruit;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Bean
@RequiredArgsConstructor
public class FruitsAccess {

  @Qualifier(value = "red-.*", regexp = true)
  private final BeanGetter<List<Fruit>> redFruits;

  public List<Fruit> allRedFruits() {
    return redFruits.get();
  }

  @Qualifier(value = "yellow-.*", regexp = true)
  private final BeanGetter<List<Fruit>> yellowFruits;

  public List<Fruit> allYellowFruits() {
    return yellowFruits.get();
  }
}

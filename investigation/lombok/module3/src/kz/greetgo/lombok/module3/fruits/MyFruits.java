package kz.greetgo.lombok.module3.fruits;

import kz.greetgo.depinject.core.Bean;

@Bean
public class MyFruits {

  @Bean(id = "black-currant")
  public Fruit blackCurrant() {
    return () -> "black currant";
  }

  @Bean(id = "red-currant")
  public Fruit redCurrant() {
    return () -> "red currant";
  }

}

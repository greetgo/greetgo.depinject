package kz.greetgo.depinject.testng.complex_tests.FactoredBy_withDefaultFactory.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

@Bean
public class Room {

  public BeanGetter<Computer> computer;

  public void typeInComputer() {
    computer.get().type();
  }

}

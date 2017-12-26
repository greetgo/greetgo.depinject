package kz.greetgo.depinject.testng.complex_tests.FactoredBy_withDefaultFactory.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.FactoredBy;

@Bean
@FactoredBy(ComputerFactory.class)
public interface Computer {
  void type();
}

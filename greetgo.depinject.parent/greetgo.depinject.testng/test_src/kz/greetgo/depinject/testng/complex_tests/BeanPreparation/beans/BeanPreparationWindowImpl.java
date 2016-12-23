package kz.greetgo.depinject.testng.complex_tests.BeanPreparation.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.testng.complex_tests.BeanPreparation.BeanPreparationTest;

@Bean
public class BeanPreparationWindowImpl implements BeanPreparationWindow {
  @Override
  public void lookOut() {
    BeanPreparationTest.log.add("Look out window");
  }
}

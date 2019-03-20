package kz.greetgo.depinject.testng.complex_tests.BeanPreparation.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.testng.complex_tests.BeanPreparation.BeanPreparationTest;

@SuppressWarnings("deprecation")
@Bean
public class BeanPreparation_WindowPreparation
  implements kz.greetgo.depinject.core.BeanPreparation<BeanPreparation_Window> {
  @Override
  public BeanPreparation_Window prepareBean(BeanPreparation_Window bean) {
    return () -> {
      BeanPreparationTest.log.add("Before look out");
      bean.lookOut();
      BeanPreparationTest.log.add("After look out");
    };
  }
}

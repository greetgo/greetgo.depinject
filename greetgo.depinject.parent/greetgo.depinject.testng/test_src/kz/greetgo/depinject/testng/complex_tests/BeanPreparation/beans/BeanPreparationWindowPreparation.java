package kz.greetgo.depinject.testng.complex_tests.BeanPreparation.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanPreparation;
import kz.greetgo.depinject.testng.complex_tests.BeanPreparation.BeanPreparationTest;

@Bean
public class BeanPreparationWindowPreparation implements BeanPreparation<BeanPreparationWindow> {
  @Override
  public BeanPreparationWindow prepareBean(BeanPreparationWindow bean) {
    return () -> {
      BeanPreparationTest.log.add("Before look out");
      bean.lookOut();
      BeanPreparationTest.log.add("After look out");
    };
  }
}

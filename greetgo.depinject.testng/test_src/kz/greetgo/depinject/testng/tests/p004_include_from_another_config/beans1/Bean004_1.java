package kz.greetgo.depinject.testng.tests.p004_include_from_another_config.beans1;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.tests.p004_include_from_another_config.Bean004;
import kz.greetgo.depinject.testng.tests.p004_include_from_another_config.beans2.Bean004_2;

@Bean
public class Bean004_1 implements Bean004 {

  public BeanGetter<Bean004_2> bean004_2;

  @Override
  public void setValue(String value) {
    bean004_2.get().setValue(value);
  }
}

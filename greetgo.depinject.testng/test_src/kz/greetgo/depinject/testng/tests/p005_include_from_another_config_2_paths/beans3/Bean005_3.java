package kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths.beans3;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths.beans2_1.Bean005_2_1;
import kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths.beans2_2.Bean005_2_2;

@Bean
public class Bean005_3 {

  public BeanGetter<Bean005_2_1> bean005_2_1;

  public void addValue1(String value) {
    bean005_2_1.get().addValue(value);
  }

  public BeanGetter<Bean005_2_2> bean005_2_2;

  public void addValue2(String value) {
    bean005_2_2.get().addValue(value);
  }
}

package kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths.beans2_1;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths.beans1.Bean005_1;

@Bean
public class Bean005_2_1 {

  public static int instanceCount = 0;

  public Bean005_2_1() {
    instanceCount++;
  }

  public BeanGetter<Bean005_1> bean005_1;

  public void addValue(String value) {
    bean005_1.get().addValue(value);
  }
}

package kz.greetgo.depinject.testng.tests.p004_include_from_another_config.beans2;

import kz.greetgo.depinject.core.Bean;

@Bean
public class Bean004_2 {

  public static String value;

  public void setValue(String value1) {
    value = value1;
  }
}

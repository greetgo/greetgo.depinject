package kz.greetgo.depinject.testng.tests.p004_include_from_another_config;

import kz.greetgo.depinject.core.Bean;

@SuppressWarnings("unused")
@Bean
public class Bean004_left implements Bean004 {
  @Override
  public void setValue(String value) {
    throw new RuntimeException("left path");
  }
}

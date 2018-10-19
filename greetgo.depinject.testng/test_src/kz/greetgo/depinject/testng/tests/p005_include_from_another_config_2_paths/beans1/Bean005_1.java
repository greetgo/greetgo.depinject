package kz.greetgo.depinject.testng.tests.p005_include_from_another_config_2_paths.beans1;

import kz.greetgo.depinject.core.Bean;

import java.util.ArrayList;
import java.util.List;

@Bean
public class Bean005_1 {

  public static final List<String> VALUE_LIST = new ArrayList<>();

  public Bean005_1() {
    VALUE_LIST.add("init");
  }

  public void addValue(String value) {
    VALUE_LIST.add(value);
  }
}

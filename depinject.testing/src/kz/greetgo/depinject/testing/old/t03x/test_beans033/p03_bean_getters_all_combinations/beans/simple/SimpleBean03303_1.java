package kz.greetgo.depinject.testing.old.t03x.test_beans033.p03_bean_getters_all_combinations.beans.simple;

import kz.greetgo.depinject.ann.Bean;

@Bean
public class SimpleBean03303_1 {
  public void hi(StringBuilder out) {
    out.append("Hi from ").append(getClass().getSimpleName()).append("\n");
  }
}

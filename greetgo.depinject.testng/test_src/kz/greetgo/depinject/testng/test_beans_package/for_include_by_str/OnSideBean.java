package kz.greetgo.depinject.testng.test_beans_package.for_include_by_str;

import kz.greetgo.depinject.core.Bean;

@Bean
public class OnSideBean {
  public String hi(String str) {
    return "Hi " + str;
  }
}

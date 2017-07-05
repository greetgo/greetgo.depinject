package kz.greetgo.depinject.testng.complex_tests.BeanReplacerFromParent.test_beans032;

import kz.greetgo.depinject.core.Bean;

@Bean
@Ann032
public class ReplacingBean032 implements ReplacingBean032Interface {
  @Override
  public void hello(int i) {
    Log032.logs032.add("ReplacingBean032.hello " + i);
  }
}

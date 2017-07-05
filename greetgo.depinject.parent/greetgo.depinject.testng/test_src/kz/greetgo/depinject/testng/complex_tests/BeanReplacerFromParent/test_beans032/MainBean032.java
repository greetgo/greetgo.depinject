package kz.greetgo.depinject.testng.complex_tests.BeanReplacerFromParent.test_beans032;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;

@Bean
public class MainBean032 {
  public BeanGetter<ReplacingBean032Interface> replacingBean032Interface;

  public void hello(int i) {
    replacingBean032Interface.get().hello(i);
  }
}

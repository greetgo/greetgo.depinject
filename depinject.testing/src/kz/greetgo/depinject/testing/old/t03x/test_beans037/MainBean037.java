package kz.greetgo.depinject.testing.old.t03x.test_beans037;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.ann.Qualifier;

@Bean
public class MainBean037 {
  @SuppressWarnings("unused")
  @Qualifier("bean2")
  public BeanGetter<BeanTarget037> beanTarget037;
}

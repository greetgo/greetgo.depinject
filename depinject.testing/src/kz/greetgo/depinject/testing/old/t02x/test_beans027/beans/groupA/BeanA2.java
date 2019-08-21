package kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.groupA;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.testing.old.t02x.test_beans027.interfaces.IBeanA1;
import kz.greetgo.depinject.testing.old.t02x.test_beans027.interfaces.IBeanA2;

@Bean(singleton = false)
public class BeanA2 implements IBeanA2 {

  public BeanGetter<IBeanA1> a1;

  @Override
  public void hi() {
    a1.get().hello();
  }

  @Bean(singleton = false)
  public FactoredBean3 createFactoredBean3() {
    return new FactoredBean3();
  }
}

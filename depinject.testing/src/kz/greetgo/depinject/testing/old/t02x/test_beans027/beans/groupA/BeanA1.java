package kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.groupA;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.testing.old.t02x.test_beans027.interfaces.IBeanA1;

@Bean
public class BeanA1 implements IBeanA1, Transactional {

  public BeanA1() throws Exception { }

  @Override
  public void hello() {
    System.out.println("hello from " + BeanA1.class);
  }

  @Bean
  public FactoredBean1 createFactoredBean1() {
    return new FactoredBean1();
  }

  @Bean(singleton = false)
  public FactoredBean2 createFactoredBean2() {
    return new FactoredBean2();
  }
}

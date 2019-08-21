package kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.groupB;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.HasAfterInject;
import kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.groupA.FactoredBean3;
import kz.greetgo.depinject.testing.old.t02x.test_beans027.interfaces.IBeanA1;
import kz.greetgo.depinject.testing.old.t02x.test_beans027.interfaces.IBeanB2;

@Bean
public class BeanB2 implements IBeanB2, HasAfterInject {

  public BeanGetter<FactoredBean3> factoredBean3Bean;

  public BeanGetter<IBeanA1> beanA1;

  @Override
  public void goodDay() {
    System.out.println("Hello from " + BeanB2.class.getSimpleName());
    factoredBean3Bean.get().helloWorld();
    beanA1.get().hello();
  }

  @Override
  public void afterInject() throws Exception {
    System.out.println("Has after inject in BeanB2");
  }

  public class ConcreteDefaultInterfaceFactory extends DefaultInterfaceFactory {
  }

  @Bean
  public ConcreteDefaultInterfaceFactory createDefaultInterfaceFactory() {
    return new ConcreteDefaultInterfaceFactory();
  }
}

package kz.greetgo.depinject.gen.beans.groupB;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.HasAfterInject;
import kz.greetgo.depinject.gen.beans.groupA.FactoredBean3;
import kz.greetgo.depinject.gen.interfaces.IBeanA1;
import kz.greetgo.depinject.gen.interfaces.IBeanB2;

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

  @Bean
  public DefaultInterfaceFactory createDefaultInterfaceFactory() {
    return new DefaultInterfaceFactory();
  }
}

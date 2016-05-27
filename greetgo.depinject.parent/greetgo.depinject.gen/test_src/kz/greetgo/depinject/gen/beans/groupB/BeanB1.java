package kz.greetgo.depinject.gen.beans.groupB;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.gen.beans.groupB.factory_one.AnotherInterfaceCreatingWithA;
import kz.greetgo.depinject.gen.beans.groupB.factory_one.InterfaceCreatingWithA;
import kz.greetgo.depinject.gen.beans.using_default_factory.CreatingByDefaultFactory;
import kz.greetgo.depinject.gen.interfaces.IBeanB1;

@Bean
public class BeanB1 implements IBeanB1 {

  @Override
  public String privet() {
    return "Privet";
  }

  public BeanGetter<InterfaceCreatingWithA> interfaceCreatingWithA;

  @Override
  public String callInterfaceCreatingWithA() {
    return interfaceCreatingWithA.get().helloFromInterfaceCreatingWithA();
  }

  public BeanGetter<AnotherInterfaceCreatingWithA> anotherInterfaceCreatingWithA;

  @Override
  public String callAnotherInterfaceCreatingWithA() {
    return anotherInterfaceCreatingWithA.get().helloFromAnotherInterfaceCreatingWithA();
  }

  public BeanGetter<CreatingByDefaultFactory> creatingByDefaultFactory;

  @Override
  public String callCreatingByDefaultFactory() {
    return creatingByDefaultFactory.get().hello();
  }
}

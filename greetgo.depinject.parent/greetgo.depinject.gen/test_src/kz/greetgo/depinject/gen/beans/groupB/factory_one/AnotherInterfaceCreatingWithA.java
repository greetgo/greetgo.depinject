package kz.greetgo.depinject.gen.beans.groupB.factory_one;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.FactoredBy;

@Bean
@FactoredBy(FactoryA.class)
public interface AnotherInterfaceCreatingWithA {
  String helloFromAnotherInterfaceCreatingWithA();
}

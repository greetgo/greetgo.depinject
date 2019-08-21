package kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.groupB.factory_one;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.ann.FactoredBy;

@Bean
@FactoredBy(FactoryA.class)
public interface AnotherInterfaceCreatingWithA {
  String helloFromAnotherInterfaceCreatingWithA();
}

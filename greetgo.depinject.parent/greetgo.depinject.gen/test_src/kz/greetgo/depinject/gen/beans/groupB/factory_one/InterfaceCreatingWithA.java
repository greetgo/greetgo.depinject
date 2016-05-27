package kz.greetgo.depinject.gen.beans.groupB.factory_one;

import kz.greetgo.depinject.core.BeanFactoredBy;

@BeanFactoredBy(FactoryA.class)
public interface InterfaceCreatingWithA {
  String helloFromInterfaceCreatingWithA();
}

package kz.greetgo.depinject.ap.engine.errors;

import kz.greetgo.depinject.ap.engine.Utils;

public class NoMethodsInBeanContainer extends RuntimeException {
  public final Class<?> beanContainerInterface;

  public NoMethodsInBeanContainer(Class<?> beanContainerInterface) {
    super(Utils.asStr(beanContainerInterface));
    this.beanContainerInterface = beanContainerInterface;
  }
}

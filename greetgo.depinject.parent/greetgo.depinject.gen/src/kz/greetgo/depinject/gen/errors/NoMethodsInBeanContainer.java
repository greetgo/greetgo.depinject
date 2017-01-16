package kz.greetgo.depinject.gen.errors;

import kz.greetgo.depinject.gen.Utils;

public class NoMethodsInBeanContainer extends RuntimeException {
  public final Class<?> beanContainerInterface;

  public NoMethodsInBeanContainer(Class<?> beanContainerInterface) {
    super(Utils.asStr(beanContainerInterface));
    this.beanContainerInterface = beanContainerInterface;
  }
}

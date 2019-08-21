package kz.greetgo.depinject.ap.engine.errors;

import java.lang.reflect.Method;

public class BeanContainerMethodCannotContainAnyArguments extends RuntimeException {
  public final Class<?> beanContainerInterface;
  public final Method method;

  public BeanContainerMethodCannotContainAnyArguments(Class<?> beanContainerInterface, Method method) {
    super(beanContainerInterface.getSimpleName() + "." + method.getName());
    this.beanContainerInterface = beanContainerInterface;
    this.method = method;
  }
}

package kz.greetgo.depinject.gen.errors;

import java.lang.reflect.Method;

public class BeanContainerMethodCannotContainsAnyArguments extends RuntimeException {
  public final Class<?> beanContainerInterface;
  public final Method method;

  public BeanContainerMethodCannotContainsAnyArguments(Class<?> beanContainerInterface, Method method) {
    super(beanContainerInterface.getSimpleName() + "." + method.getName());
    this.beanContainerInterface = beanContainerInterface;
    this.method = method;
  }
}

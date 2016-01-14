package kz.greetgo.depinject.gen.errors;

import java.lang.reflect.Method;

public class BeanFactoryMethodCannotHasAnyArguments extends RuntimeException {
  public final Method factoryMethod;

  public BeanFactoryMethodCannotHasAnyArguments(Method factoryMethod) {
    super(factoryMethod.toGenericString());
    this.factoryMethod = factoryMethod;
  }
}

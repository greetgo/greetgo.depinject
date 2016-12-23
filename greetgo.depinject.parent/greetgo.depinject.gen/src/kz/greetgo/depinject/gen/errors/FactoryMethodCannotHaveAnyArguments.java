package kz.greetgo.depinject.gen.errors;

import java.lang.reflect.Method;

public class FactoryMethodCannotHaveAnyArguments extends RuntimeException {
  public final Method factoryMethod;

  public FactoryMethodCannotHaveAnyArguments(Method factoryMethod) {
    super(factoryMethod.toGenericString());
    this.factoryMethod = factoryMethod;
  }
}

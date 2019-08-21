package kz.greetgo.depinject.ap.engine.errors;

import java.lang.reflect.Method;

public class FactoryMethodCannotContainAnyArguments extends RuntimeException {
  public final Method factoryMethod;

  public FactoryMethodCannotContainAnyArguments(Method factoryMethod) {
    super(factoryMethod.toGenericString());
    this.factoryMethod = factoryMethod;
  }
}

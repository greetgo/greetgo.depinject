package kz.greetgo.depinject.gen2;

import java.lang.reflect.Method;

public class BeanCreationWithFactoryMethod extends BeanCreation {
  public final BeanCreation factorySource;
  public final Method factoryMethod;

  public BeanCreationWithFactoryMethod(Class<?> beanClass, boolean singleton, 
                                       BeanCreation factorySource, Method factoryMethod) {
    super(beanClass, singleton);
    this.factorySource = factorySource;
    this.factoryMethod = factoryMethod;
  }
}

package kz.greetgo.depinject.gen;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ConstructorArg {
  private final BeanReference beanReference;

  public ConstructorArg(BeanReference beanReference) {
    this.beanReference = beanReference;
  }

  public BeanReference beanReference() {
    return beanReference;
  }

  public String displayStr() {
    throw new NotImplementedException();
  }

  public void markToUse() {
    throw new NotImplementedException();
  }
}

package kz.greetgo.depinject.gen;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Type;

public class ConstructorArg {
  public final Type argType;
  public final BeanReference beanReference;

  public ConstructorArg(Type argType, BeanReference beanReference) {
    this.argType = argType;
    this.beanReference = beanReference;
  }

  public String displayStr() {
    throw new NotImplementedException();
  }

  public void markToUse() {
    throw new NotImplementedException();
  }

  public BeanReference beanReference() {
    return beanReference;
  }
}

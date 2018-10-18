package kz.greetgo.depinject.gen;

import java.lang.reflect.Type;

public class ConstructorArg {
  public final Type argType;
  public final BeanReference beanReference;

  public ConstructorArg(Type argType, BeanReference beanReference) {
    this.argType = argType;
    this.beanReference = beanReference;
  }

  public String displayStr() {
    return "Left str hs765s6dys6";
  }

  public void markToUse() {
    // TODO: 18.10.18 realize it
  }

  public BeanReference beanReference() {
    return beanReference;
  }
}

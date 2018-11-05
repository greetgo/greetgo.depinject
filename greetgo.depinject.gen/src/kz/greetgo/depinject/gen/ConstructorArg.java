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
    return Utils.typeAsStr(argType);
  }

  public void markToUse() {
    beanReference.markToUse();
  }

  public BeanReference beanReference() {
    return beanReference;
  }

  public String referenceExpression() {
    return beanReference.accessExpression();
  }
}

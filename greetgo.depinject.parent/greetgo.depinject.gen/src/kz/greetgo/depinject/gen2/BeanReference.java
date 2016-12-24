package kz.greetgo.depinject.gen2;

import java.lang.reflect.Type;

public class BeanReference {
  public final Type target;

  public BeanReference(Type target) {
    this.target = target;
  }

  public Class<?> targetClass() {
    if (target instanceof Class) return (Class<?>) target;
    throw new RuntimeException("Cannot extract class from type: " + target.toString());
  }
}

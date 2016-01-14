package kz.greetgo.depinject.gen.errors;

import java.lang.reflect.Type;

public class MoreThenOneBeanClassIsAssignable extends RuntimeException {
  
  public final Type fieldType;
  public final Class<?> bean1;
  public final Class<?> bean2;
  
  public MoreThenOneBeanClassIsAssignable(Type fieldType, Class<?> bean1, Class<?> bean2) {
    super("beaningClass = " + fieldType.toString() + " : bean1 = " + bean1.getSimpleName()
        + ", bean2 = " + bean2.getSimpleName());
    this.fieldType = fieldType;
    this.bean1 = bean1;
    this.bean2 = bean2;
  }
  
}

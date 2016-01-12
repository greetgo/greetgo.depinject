package kz.greetgo.depinject.gwt.gen;

public class MoreThenOneBeanClassIsAssignable extends RuntimeException {
  
  public final Class<?> fieldType;
  public final Class<?> bean1;
  public final Class<?> bean2;
  
  public MoreThenOneBeanClassIsAssignable(Class<?> fieldType, Class<?> bean1, Class<?> bean2) {
    super("beaningClass = " + fieldType.getSimpleName() + " : bean1 = " + bean1.getSimpleName()
        + ", bean2 = " + bean2.getSimpleName());
    this.fieldType = fieldType;
    this.bean1 = bean1;
    this.bean2 = bean2;
  }
  
}

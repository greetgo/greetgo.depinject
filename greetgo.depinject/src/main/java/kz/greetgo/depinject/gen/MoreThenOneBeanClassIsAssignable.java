package kz.greetgo.depinject.gen;

public class MoreThenOneBeanClassIsAssignable extends RuntimeException {
  
  public final Class<?> beaningClass;
  public final Class<?> bean1;
  public final Class<?> bean2;
  
  public MoreThenOneBeanClassIsAssignable(Class<?> beaningClass, Class<?> bean1, Class<?> bean2) {
    super("beaningClass = " + beaningClass.getSimpleName() + " : bean1 = " + bean1.getSimpleName()
        + ", bean2 = " + bean2.getSimpleName());
    this.beaningClass = beaningClass;
    this.bean1 = bean1;
    this.bean2 = bean2;
  }
  
}

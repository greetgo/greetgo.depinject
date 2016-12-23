package kz.greetgo.depinject.gen2;

public abstract class BeanCreation {
  public final Class<?> beanClass;
  public final boolean singleton;

  public BeanCreation(Class<?> beanClass, boolean singleton) {
    this.beanClass = beanClass;
    this.singleton = singleton;
  }
  
  
}

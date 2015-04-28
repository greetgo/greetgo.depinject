package kz.greetgo.depinject.gen;

public class NoMatchingBeanFor extends RuntimeException {
  
  public final Class<?> beaningClass;
  
  public NoMatchingBeanFor(Class<?> beaningClass) {
    super(beaningClass.getName());
    this.beaningClass = beaningClass;
  }
  
}

package kz.greetgo.depinject.gen.errors;

public class NoDefaultBeanFactory extends RuntimeException {

  public final Class<?> beanClass;

  public NoDefaultBeanFactory(Class<?> beanClass) {
    super("For " + beanClass);
    this.beanClass = beanClass;
  }
}

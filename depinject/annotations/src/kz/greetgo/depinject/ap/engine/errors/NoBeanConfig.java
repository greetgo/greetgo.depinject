package kz.greetgo.depinject.ap.engine.errors;

public class NoBeanConfig extends RuntimeException {
  public final Class<?> beanConfigClass;

  public NoBeanConfig(Class<?> beanConfigClass) {
    super("Add annotation BeanConfig to " + beanConfigClass.getName());
    this.beanConfigClass = beanConfigClass;
  }
}

package kz.greetgo.depinject.ap.engine.errors;

public class NoBeanContainer extends RuntimeException {
  public final Class<?> beanContainerInterface;

  public NoBeanContainer(Class<?> beanContainerInterface) {
    super(beanContainerInterface.getName());
    this.beanContainerInterface = beanContainerInterface;
  }
}

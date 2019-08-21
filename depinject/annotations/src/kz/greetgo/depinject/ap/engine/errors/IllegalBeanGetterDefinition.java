package kz.greetgo.depinject.ap.engine.errors;

public class IllegalBeanGetterDefinition extends RuntimeException {
  public final Class<?> beanClass;
  public final String fieldName;

  public IllegalBeanGetterDefinition(Class<?> beanClass, String fieldName) {
    super("Field " + fieldName + " in " + beanClass);
    this.beanClass = beanClass;
    this.fieldName = fieldName;
  }
}

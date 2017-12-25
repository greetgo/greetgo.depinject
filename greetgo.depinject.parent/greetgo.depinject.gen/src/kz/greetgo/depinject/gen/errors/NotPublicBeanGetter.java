package kz.greetgo.depinject.gen.errors;

import java.lang.reflect.Field;

public class NotPublicBeanGetter extends RuntimeException {
  public final Class<?> aClass;
  public final Field beanGetterField;

  public NotPublicBeanGetter(Class<?> aClass, Field beanGetterField) {
    super("Field " + beanGetterField.getName() + " is not public in " + aClass);
    this.aClass = aClass;
    this.beanGetterField = beanGetterField;
  }
}

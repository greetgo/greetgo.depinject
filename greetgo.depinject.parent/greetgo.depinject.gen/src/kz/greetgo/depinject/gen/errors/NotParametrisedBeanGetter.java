package kz.greetgo.depinject.gen.errors;

import java.lang.reflect.Field;

public class NotParametrisedBeanGetter extends RuntimeException {
  public final Field beanField;
  public final Class<?> beanClass;

  public NotParametrisedBeanGetter(Field beanField, Class<?> beanClass) {
    super(beanClass.getSimpleName() + "." + beanField.getName());
    this.beanField = beanField;
    this.beanClass = beanClass;
  }
}

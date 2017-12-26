package kz.greetgo.depinject.gen.errors;

import java.lang.reflect.Field;

public class BeanGetterIsNotPublic extends RuntimeException {
  public final Class<?> containsFieldClass;
  public final Field beanGetterField;
  public final Class<?> beanClass;

  public BeanGetterIsNotPublic(Class<?> containsFieldClass, Field beanGetterField, Class<?> beanClass) {
    super("Field " + beanGetterField.getName() + " is not public in "
      + containsFieldClass + (beanClass == null ? "" : ": it is the parent of bean " + beanClass));
    this.containsFieldClass = containsFieldClass;
    this.beanGetterField = beanGetterField;
    this.beanClass = beanClass;
  }
}

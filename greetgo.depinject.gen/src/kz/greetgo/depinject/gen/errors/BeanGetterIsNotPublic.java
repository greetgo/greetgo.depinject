package kz.greetgo.depinject.gen.errors;

import java.lang.reflect.Field;

public class BeanGetterIsNotPublic extends RuntimeException {
  public final Class<?> containsFieldClass;
  public final Field beanGetterField;
  public final Class<?> beanClass;

  public BeanGetterIsNotPublic(Class<?> containsFieldClass, Field beanGetterField, Class<?> beanClass) {
    super("Field " + beanGetterField.getName() + " is not public in "
      + containsFieldClass + (beanClass == null ? "" : ": it is the parent of bean " + beanClass) + ".\n" +
      "\tIf you want none public BeanGetter field you may add annotation @LetBeNonePublic to field or class");
    this.containsFieldClass = containsFieldClass;
    this.beanGetterField = beanGetterField;
    this.beanClass = beanClass;
  }
}

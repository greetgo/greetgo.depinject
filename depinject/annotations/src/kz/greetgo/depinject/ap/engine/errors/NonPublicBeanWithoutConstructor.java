package kz.greetgo.depinject.ap.engine.errors;

import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.ann.SkipInject;

import java.lang.reflect.Field;

public class NonPublicBeanWithoutConstructor extends RuntimeException {
  public final Class<?> containsFieldClass;
  public final Field beanGetterField;
  public final Class<?> beanClass;

  public NonPublicBeanWithoutConstructor(Class<?> containsFieldClass, Field beanGetterField, Class<?> beanClass) {
    super(message(containsFieldClass, beanGetterField, beanClass));
    this.containsFieldClass = containsFieldClass;
    this.beanGetterField = beanGetterField;
    this.beanClass = beanClass;
  }

  private static String message(Class<?> containsFieldClass, Field beanGetterField, Class<?> beanClass) {
    String fieldName = beanGetterField.getName();
    String bg = BeanGetter.class.getSimpleName();
    String si = SkipInject.class.getSimpleName();

    return "Field " + fieldName + " is not public in "
        + containsFieldClass + (beanClass == null ? "" : ": it is the parent of bean " + beanClass) + "."
        + "\n\tDepinject cannot inject into not public field."
        + "\n\tIf you still want to use not public " + bg + " field"
        + " you may add annotation @" + si + " to this field for success build."
        + "\n\tATTENTION: this field will be NULL.";
  }
}

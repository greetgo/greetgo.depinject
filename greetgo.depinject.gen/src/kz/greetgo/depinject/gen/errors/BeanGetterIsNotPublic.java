package kz.greetgo.depinject.gen.errors;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.SkipInject;

import java.lang.reflect.Field;

public class BeanGetterIsNotPublic extends RuntimeException {
  public final Class<?> containsFieldClass;
  public final Field beanGetterField;
  public final Class<?> beanClass;

  public BeanGetterIsNotPublic(Class<?> containsFieldClass, Field beanGetterField, Class<?> beanClass) {
    super("Field " + beanGetterField.getName() + " is not public in "
        + containsFieldClass + (beanClass == null ? "" : ": it is the parent of bean " + beanClass) + ".\n" +
        "\tDepinject cannot inject into not public field.\n" +
        "\tIf you still want to use not public " + BeanGetter.class.getSimpleName()
        + " field you may add annotation @" + SkipInject.class.getSimpleName() + " to this field for success build.\n" +
        "\tATTENTION: this field will be NULL.");
    this.containsFieldClass = containsFieldClass;
    this.beanGetterField = beanGetterField;
    this.beanClass = beanClass;
  }
}

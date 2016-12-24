package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.gen.errors.IllegalBeanGetterDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BeanCreation implements Comparable<BeanCreation> {
  public final Class<?> beanClass;
  public final boolean singleton;

  public BeanCreation(Class<?> beanClass, boolean singleton) {
    this.beanClass = beanClass;
    this.singleton = singleton;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + '{' + (singleton ? "singleton" : "multiple") + ' ' + beanClass + '}';
  }

  public final List<BeanGetterDot> beanGetterDotList = new ArrayList<>();

  public void fillBeanGetterDotList() {
    fillBeanGetterDotListInner(beanGetterDotList, beanClass);
  }

  static void fillBeanGetterDotListInner(List<BeanGetterDot> beanGetterDotList, Class<?> beanClass) {

    for (Field field : beanClass.getFields()) {
      if (field.getType() == BeanGetter.class) {
        addDot(beanGetterDotList, field.getName(), field.getGenericType(), beanClass);
      }
    }

    Collections.sort(beanGetterDotList);

  }

  private static void addDot(List<BeanGetterDot> list, String fieldName, Type beanGetterType, Class<?> beanClass) {
    if (!(beanGetterType instanceof ParameterizedType)) {
      throw new IllegalBeanGetterDefinition(beanClass, fieldName);
    }
    ParameterizedType pt = (ParameterizedType) beanGetterType;
    BeanReference beanReference = new BeanReference(pt.getActualTypeArguments()[0],
      "in field " + fieldName + " of " + beanClass);
    list.add(new BeanGetterDot(fieldName, beanReference));
  }

  protected String compareStr() {
    return beanClass.getName();
  }

  @Override
  @SuppressWarnings("NullableProblems")
  public int compareTo(BeanCreation o) {
    return compareStr().compareTo(o.compareStr());
  }
}

package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.BeanPreparation;
import kz.greetgo.depinject.core.BeanPreparationPriority;
import kz.greetgo.depinject.gen.errors.IllegalBeanGetterDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static kz.greetgo.depinject.gen2.Utils.extractRawClass;

public abstract class BeanCreation implements Comparable<BeanCreation> {
  public final Class<?> beanClass;
  public final boolean singleton;

  public boolean use = false;

  public BeanCreation(Class<?> beanClass, boolean singleton) {
    this.beanClass = beanClass;
    this.singleton = singleton;
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
      "field " + fieldName + " of " + Utils.asStr(beanClass));
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

  public void markToUse() {
    if (use) return;
    use = true;
    beanGetterDotList.forEach(a -> a.beanReference.markToUse());
    markToUseAdditions();
  }

  protected abstract void markToUseAdditions();

  public static class BeanPreparationPriorityDot implements Comparable<BeanPreparationPriorityDot> {
    int parenting = 0;
    double fromAnnotation = 0;

    @Override
    @SuppressWarnings("NullableProblems")
    public int compareTo(BeanPreparationPriorityDot o) {
      {
        int cmp = Integer.compare(parenting, o.parenting);
        if (cmp != 0) return cmp;
      }
      return Double.compare(fromAnnotation, o.fromAnnotation);
    }

    @Override
    public String toString() {
      return "(" + parenting + ", " + fromAnnotation + ')';
    }
  }

  private final BeanPreparationPriorityDot beanPreparationPriority = new BeanPreparationPriorityDot();

  public void calculatesBeanPreparationPriority(List<BeanCreation> preparations) {
    {
      BeanPreparationPriority annotation = Utils.getAnnotation(beanClass, BeanPreparationPriority.class);
      if (annotation != null) beanPreparationPriority.fromAnnotation = annotation.value();
    }

    preparations.stream()
      .filter(bc -> bc.preparingClass != null)
      .forEach(that -> {
        if (that.preparingClass.isAssignableFrom(this.preparingClass)) {
          that.beanPreparationPriority.parenting++;
        }
      });
  }

  public BeanPreparationPriorityDot beanPreparationPriority() {
    return beanPreparationPriority;
  }

  public Class<?> preparingClass = null;

  public void calculatePreparingClass() {
    preparingClass = getPreparingClass(beanClass, new HashSet<>());
  }

  static Class<?> getPreparingClass(Type type, Set<Class<?>> cacheSet) {

    if (type == null) return null;

    if (type instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type;
      Type rawType = parameterizedType.getRawType();

      if (rawType instanceof Class) {
        Class<?> rawClass = (Class<?>) rawType;
        if (BeanPreparation.class == rawClass) {
          Type typeArg0 = parameterizedType.getActualTypeArguments()[0];
          return extractRawClass(typeArg0);
        }
      }

      type = extractRawClass(rawType);
    }

    if (type instanceof Class) {
      Class<?> aClass = (Class<?>) type;
      if (cacheSet.contains(aClass)) return null;
      cacheSet.add(aClass);

      for (Type interfaceType : aClass.getGenericInterfaces()) {
        Class<?> ret = getPreparingClass(interfaceType, cacheSet);
        if (ret != null) return ret;
      }

      return getPreparingClass(aClass.getSuperclass(), cacheSet);
    }

    throw new IllegalArgumentException("Unknown type: " + type);
  }

  protected String preparationInfo() {
    if (preparingClass == null) return "";
    return ", preparation for " + Utils.asStr(preparingClass) + ' ' + beanPreparationPriority.toString();
  }
}

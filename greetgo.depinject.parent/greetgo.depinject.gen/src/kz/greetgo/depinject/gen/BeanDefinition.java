package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.BeanPreparation;
import kz.greetgo.depinject.gen.errors.MoreThenOneBeanClassIsAssignable;
import kz.greetgo.depinject.gen.errors.NoMatchingBeanFor;
import kz.greetgo.depinject.gen.errors.NotParametrisedBeanGetter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static kz.greetgo.depinject.gen.DepinjectUtil.notNull;
import static kz.greetgo.depinject.gen.DepinjectUtil.typeToClass;

public class BeanDefinition implements Comparable<BeanDefinition> {
  public final Class<?> beanClass;
  public final boolean singleton;

  public final Class<?> beanClassFactory;
  public final Method factoryMethod;

  public String creationCode(Map<Class<?>, BeanDefinition> map) {
    if (beanClassFactory == null) {
      return "new " + beanClass.getName() + "()";
    }
    final BeanDefinition factoryDefinition = map.get(beanClassFactory);
    return factoryDefinition.getterName + ".get()." + factoryMethod.getName() + "()";
  }


  public final List<Injector> injectors = new ArrayList<>();

  public Class<?> prepareReferenceClass = null;

  public final List<BeanDefinition> preparingBy = new ArrayList<>();
  public final Set<BeanDefinition> using = new HashSet<>();

  public String getterName;

  public BeanDefinition(Class<?> beanClass, boolean singleton, Class<?> beanClassFactory, Method factoryMethod) {
    notNull(beanClass);
    this.beanClass = beanClass;
    this.singleton = singleton;
    this.beanClassFactory = beanClassFactory;
    this.factoryMethod = factoryMethod;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("BeanDefinition{");
    sb.append(singleton ? "SINGLE " : "MULTIPLE ");
    sb.append(beanClass.getSimpleName());
    if (beanClassFactory != null) {
      sb.append(" factored from ");
      sb.append(beanClassFactory.getSimpleName());
      sb.append(" with ").append(factoryMethod.getName());
    }
    return sb.append('}').toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BeanDefinition that = (BeanDefinition) o;

    if (singleton != that.singleton) return false;
    if (beanClass != null ? !beanClass.equals(that.beanClass) : that.beanClass != null) return false;
    if (beanClassFactory != null ? !beanClassFactory.equals(that.beanClassFactory) : that.beanClassFactory != null)
      return false;
    return !(factoryMethod != null ? !factoryMethod.equals(that.factoryMethod) : that.factoryMethod != null);

  }

  @Override
  public int hashCode() {
    int result = beanClass != null ? beanClass.hashCode() : 0;
    result = 31 * result + (singleton ? 1 : 0);
    result = 31 * result + (beanClassFactory != null ? beanClassFactory.hashCode() : 0);
    result = 31 * result + (factoryMethod != null ? factoryMethod.hashCode() : 0);
    return result;
  }

  public void initInjectors(Map<Class<?>, BeanDefinition> map) {
    for (Field field : beanClass.getFields()) {
      if (field.getType().equals(BeanGetter.class)) {
        addInjectorFor(field, map);
      }
    }
    Collections.sort(injectors);
  }

  private void addInjectorFor(Field field, Map<Class<?>, BeanDefinition> map) {
    final Type fieldGenericType = field.getGenericType();
    if (!(fieldGenericType instanceof ParameterizedType)) {
      throw new NotParametrisedBeanGetter(field, beanClass);
    }
    ParameterizedType fieldType = (ParameterizedType) fieldGenericType;

    final BeanDefinition to = findBeanDefinition(fieldType.getActualTypeArguments()[0], map);
    injectors.add(new Injector(this, field, to));
  }

  public static BeanDefinition findBeanDefinition(Type referenceType, Map<Class<?>, BeanDefinition> map) {

    BeanDefinition ret = null;

    for (BeanDefinition beanDefinition : map.values()) {
      if (beanDefinition.isFor(referenceType)) {
        if (ret == null) {
          ret = beanDefinition;
        } else {
          throw new MoreThenOneBeanClassIsAssignable(referenceType, ret.beanClass, beanDefinition.beanClass);
        }
      }
    }

    if (ret == null) throw new NoMatchingBeanFor(referenceType);

    return ret;
  }

  private boolean isFor(Type referenceType) {
    final Class<?> referenceClass = typeToClass(referenceType);
    return referenceClass.isAssignableFrom(beanClass);
  }


  public void initFieldPrepareReferenceClass() {
    if (!BeanPreparation.class.isAssignableFrom(beanClass)) return;

    prepareReferenceClass = extractPrepareReferenceClass(beanClass);
  }

  private static Class<?> extractPrepareReferenceClass(Class<?> aClass) {

    if (Object.class.equals(aClass)) return null;

    for (Type type : aClass.getGenericInterfaces()) {
      if (type instanceof ParameterizedType) {
        ParameterizedType parameterizedType = (ParameterizedType) type;
        if (parameterizedType.getRawType().equals(BeanPreparation.class)) {
          return typeToClass(parameterizedType.getActualTypeArguments()[0]);
        }
      }
      {
        final Class<?> retClass = extractPrepareReferenceClass(typeToClass(type));
        if (retClass != null) return retClass;
      }
    }

    return extractPrepareReferenceClass(aClass.getSuperclass());

  }

  public void initFieldPreparingBy(Map<Class<?>, BeanDefinition> map) {

    for (BeanDefinition beanDefinition : map.values()) {
      if (beanDefinition.prepareReferenceClass == null) continue;
      if (beanDefinition.prepareReferenceClass.isAssignableFrom(beanClass)) {
        preparingBy.add(beanDefinition);
      }
    }

  }

  public void initUsing(Map<Class<?>, BeanDefinition> map) {
    if (beanClassFactory != null) using.add(notNull(map.get(beanClassFactory)));

    for (Injector injector : injectors) {
      using.add(injector.to);
    }

    for (BeanDefinition beanDefinition : preparingBy) {
      using.add(beanDefinition);
    }

  }

  @Override
  public int compareTo(BeanDefinition o) {
    return beanClass.getSimpleName().compareTo(o.beanClass.getSimpleName());
  }

}

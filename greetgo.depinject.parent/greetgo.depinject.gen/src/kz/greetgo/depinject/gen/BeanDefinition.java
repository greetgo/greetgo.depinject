package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.BeanPreparation;
import kz.greetgo.depinject.gen.errors.CannotInjectTo;
import kz.greetgo.depinject.gen.errors.MoreThenOneBeanClassIsAssignable;
import kz.greetgo.depinject.gen.errors.NoMatchingBeanFor;
import kz.greetgo.depinject.gen.errors.NotParametrisedBeanGetter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static kz.greetgo.depinject.gen.DepinjectUtil.typeToClass;
import static kz.greetgo.util.ServerUtil.notNull;

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
  public boolean hasAfterInject = false;

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
    //noinspection SimplifiableIfStatement
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
      if (field.getType() == BeanGetter.class) {
        addInjectorFor(field, map);
      }
    }
    Collections.sort(injectors);
  }

  private void addInjectorFor(Field field, Map<Class<?>, BeanDefinition> map) {
    final Type beanGetterGenericType = field.getGenericType();
    if (!(beanGetterGenericType instanceof ParameterizedType)) {
      throw new NotParametrisedBeanGetter(field, beanClass);
    }
    ParameterizedType beanGetterParameterizedType = (ParameterizedType) beanGetterGenericType;

    final Type destinationType = beanGetterParameterizedType.getActualTypeArguments()[0];
    if (destinationType instanceof ParameterizedType) {
      ParameterizedType parameterizedDestinationType = (ParameterizedType) destinationType;

      if (parameterizedDestinationType.getRawType() == List.class) {
        final List<BeanDefinition> sourceList = findAllBeanDefinitions(
          parameterizedDestinationType.getActualTypeArguments()[0], map, beanClass.toString());
        injectors.add(new InjectorList(this, field, sourceList));
        return;
      }

      throw new CannotInjectTo(field);
    }


    final BeanDefinition source = findBeanDefinition(destinationType, map, beanClass.toString());
    injectors.add(new InjectorSingle(this, field, source));
  }

  private static List<BeanDefinition> findAllBeanDefinitions(Type referenceType,
                                                             Map<Class<?>, BeanDefinition> map,
                                                             String place) {
    List<BeanDefinition> ret = new ArrayList<>();

    for (BeanDefinition beanDefinition : map.values()) {
      if (beanDefinition.isFor(referenceType)) {
        ret.add(beanDefinition);
      }
    }

    if (ret.isEmpty()) throw new NoMatchingBeanFor(referenceType, place);

    return ret;
  }

  public static BeanDefinition findBeanDefinition(Type referenceType, Map<Class<?>, BeanDefinition> map, String place) {

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

    if (ret == null) throw new NoMatchingBeanFor(referenceType, place);

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
        if (parameterizedType.getRawType() == BeanPreparation.class) {
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
      using.addAll(injector.sourceList());
    }

    for (BeanDefinition beanDefinition : preparingBy) {
      using.add(beanDefinition);
    }

  }

  @SuppressWarnings("NullableProblems")
  @Override
  public int compareTo(BeanDefinition o) {
    return beanClass.getSimpleName().compareTo(o.beanClass.getSimpleName());
  }

}

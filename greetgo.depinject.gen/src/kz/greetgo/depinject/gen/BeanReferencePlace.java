package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.FactoredBy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class BeanReferencePlace {
  public static BeanReference.Place placeInPublicBeanGetter(Type referencingClass, Class<?> beanClass, Field field) {
    return new BeanReference.Place() {
      @Override
      public void asd() {

      }

      @Override
      public String display() {
        return "field " + field.getName() + " of " + Utils.asStr(beanClass);
      }
    };
  }

  public static BeanReference.Place placeInConstructorArg(Type referencingType,
                                                          Type argType, int argIndex,
                                                          Class<?> beanClass) {
    return new BeanReference.Place() {
      @Override
      public void asd() {

      }

      @Override
      public String display() {
        return "argument № " + argIndex + " of constructor of " + Utils.asStr(beanClass);
      }
    };
  }

  public static BeanReference.Place placeInBeanFactoryOf(Class<?> beanConfig) {
    return new BeanReference.Place() {
      @Override
      public void asd() {

      }

      @Override
      public String display() {
        return "bean factory of " + Utils.asStr(beanConfig);
      }
    };
  }

  public static BeanReference.Place placeInAnnotationFactoredBy(Class<?> beanClass) {
    return new BeanReference.Place() {
      @Override
      public void asd() {

      }

      @Override
      public String display() {
        return FactoredBy.class.getSimpleName() + " in (or in any parents of) " + Utils.asStr(beanClass);
      }
    };
  }

  public static BeanReference.Place placeInBeanContainerMethod(Method method) {
    return new BeanReference.Place() {
      @Override
      public void asd() {

      }

      @Override
      public String display() {
        return "return type of method " + method.getName() + "() of " + Utils.asStr(method.getDeclaringClass());
      }
    };
  }
}

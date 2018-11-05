package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.FactoredBy;
import kz.greetgo.depinject.core.Qualifier;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class BeanReferencePlace {
  public static BeanReference.Place placeInPublicBeanGetter(Type referencingClass, Class<?> beanClass, Field field) {
    return new BeanReference.Place() {
      @Override
      public BeanReference.PlaceType type() {
        return BeanReference.PlaceType.InPublicBeanGetter;
      }

      @Override
      public String display() {
        return "public field " + Utils.asStr(beanClass) + "." + field.getName() + " -> " + referencingClass;
      }

      @Override
      public String qualifier() {
        Qualifier qualifier = field.getAnnotation(Qualifier.class);
        return qualifier == null ? "" : qualifier.value();
      }
    };
  }

  public static BeanReference.Place placeInConstructorArg(Type referencingType,
                                                          Type argType, int argIndex,
                                                          Class<?> beanClass) {
    return new BeanReference.Place() {
      @Override
      public BeanReference.PlaceType type() {
        return BeanReference.PlaceType.InConstructorArg;
      }

      @Override
      public String display() {
        return "argument " + argIndex + " of constructor in " + Utils.asStr(beanClass);
      }

      @Override
      public String qualifier() {
        throw new NotImplementedException();
      }
    };
  }

  public static BeanReference.Place placeInBeanFactory(Class<?> beanConfig) {
    return new BeanReference.Place() {
      @Override
      public BeanReference.PlaceType type() {
        return BeanReference.PlaceType.InBeanFactory;
      }

      @Override
      public String display() {
        return "bean factory of " + Utils.asStr(beanConfig);
      }

      @Override
      public String qualifier() {
        throw new NotImplementedException();
      }
    };
  }

  public static BeanReference.Place placeInAnnotationFactoredBy(Class<?> beanClass) {
    return new BeanReference.Place() {
      @Override
      public BeanReference.PlaceType type() {
        return BeanReference.PlaceType.InAnnotationFactoredBy;
      }

      @Override
      public String display() {
        return FactoredBy.class.getSimpleName() + " in (or in any parents of) " + Utils.asStr(beanClass);
      }

      @Override
      public String qualifier() {
        throw new NotImplementedException();
      }
    };
  }

  public static BeanReference.Place placeInBeanContainerMethod(Method method) {
    return new BeanReference.Place() {
      @Override
      public BeanReference.PlaceType type() {
        return BeanReference.PlaceType.InBeanContainerMethod;
      }

      @Override
      public String display() {
        return "bean container method " + Utils.asStr(method.getDeclaringClass()) + "." + method.getName() + "()";
      }

      @Override
      public String qualifier() {
        throw new NotImplementedException();
      }
    };
  }
}

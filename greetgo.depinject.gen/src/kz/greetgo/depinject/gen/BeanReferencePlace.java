package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.FactoredBy;
import kz.greetgo.depinject.core.Qualifier;
import kz.greetgo.depinject.gen.errors.NoFieldSpecifiedInAnnotationConstructorProperties;

import java.beans.ConstructorProperties;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

import static kz.greetgo.depinject.gen.Utils.beanConfigToQualifier;
import static kz.greetgo.depinject.gen.Utils.factoredByToQualifier;
import static kz.greetgo.depinject.gen.Utils.findDeclaredField;
import static kz.greetgo.depinject.gen.Utils.noneNull;
import static kz.greetgo.depinject.gen.Utils.typeAsStr;

public class BeanReferencePlace {
  public static BeanReference.Place placeInPublicBeanGetter(Type referencingClass, Class<?> beanClass, Field field) {
    return new BeanReference.Place() {
      @Override
      public BeanReference.PlaceType type() {
        return BeanReference.PlaceType.InPublicBeanGetter;
      }

      @Override
      public String display() {
        return "public field " + Utils.asStr(beanClass) + "." + field.getName()
          + " -> " + typeAsStr(referencingClass) + qualifierAsStr(qualifier());
      }

      @Override
      public Qualifier qualifier() {
        return noneNull(field.getAnnotation(Qualifier.class));
      }
    };
  }

  private static String qualifierAsStr(Qualifier qualifier) {
    if (qualifier == null || qualifier.value().isEmpty()) {
      return "";
    }

    return qualifier.regexp() ? " [id ~ /" + qualifier.value() + "/]" : " [id = " + qualifier.value() + "]";
  }

  public static BeanReference.Place placeInConstructorArg(Type referencingType,
                                                          Type argType, int argIndex,
                                                          Class<?> beanClass,
                                                          Constructor<?> constructor) {
    return new BeanReference.Place() {
      @Override
      public BeanReference.PlaceType type() {
        return BeanReference.PlaceType.InConstructorArg;
      }

      @Override
      public String display() {
        return "argument №" + argIndex + " type " + argType + " of constructor in " + Utils.asStr(beanClass)
          + " -> " + typeAsStr(referencingType) + qualifierAsStr(qualifier());
      }

      @Override
      public Qualifier qualifier() {

        ConstructorProperties cp = constructor.getAnnotation(ConstructorProperties.class);
        assert cp != null;
        //always not null because it validated:
        //   in method ArgSet.validateArguments()
        //   in method Context.newBeanCreationWithConstructor(...)

        Field field = findDeclaredField(beanClass, cp.value()[argIndex])
          .orElseThrow(() -> new NoFieldSpecifiedInAnnotationConstructorProperties(
            beanClass, cp.value()[argIndex], constructor));

        Qualifier fieldQualifier = field.getAnnotation(Qualifier.class);

        if (fieldQualifier != null) {
          return fieldQualifier;
        }

        return Arrays.stream(constructor.getParameterAnnotations()[argIndex])
          .filter(a -> a instanceof Qualifier)
          .map(a -> (Qualifier) a)
          .findAny()
          .orElse(noneNull(null));
      }
    };
  }

  public static BeanReference.Place placeInBeanFactory(Class<?> beanConfig, BeanConfig beanConfigAnn) {
    return new BeanReference.Place() {
      @Override
      public BeanReference.PlaceType type() {
        return BeanReference.PlaceType.InBeanFactory;
      }

      @Override
      public String display() {
        return "bean factory of " + Utils.asStr(beanConfig) + qualifierAsStr(qualifier());
      }

      @Override
      public Qualifier qualifier() {
        return beanConfigToQualifier(beanConfigAnn);
      }
    };
  }

  public static BeanReference.Place placeInAnnotationFactoredBy(Class<?> beanClass, FactoredBy factoredBy) {
    return new BeanReference.Place() {
      @Override
      public BeanReference.PlaceType type() {
        return BeanReference.PlaceType.InAnnotationFactoredBy;
      }

      @Override
      public String display() {
        return factoredBy.getClass().getSimpleName()
          + " in (or in any parents of) " + Utils.asStr(beanClass) + qualifierAsStr(qualifier());
      }

      @Override
      public Qualifier qualifier() {
        return factoredByToQualifier(factoredBy);
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
        return "bean container method "
          + Utils.asStr(method.getDeclaringClass()) + "." + method.getName() + "()"
          + qualifierAsStr(qualifier());
      }

      @Override
      public Qualifier qualifier() {
        return noneNull(method.getAnnotation(Qualifier.class));
      }
    };
  }

}

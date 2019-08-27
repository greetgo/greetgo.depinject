package kz.greetgo.depinject.ap.engine;

import kz.greetgo.depinject.ann.BeanConfig;
import kz.greetgo.depinject.ann.FactoredBy;
import kz.greetgo.depinject.ann.Qualifier;
import kz.greetgo.depinject.ann.util.Place;
import kz.greetgo.depinject.ann.util.PlaceType;
import kz.greetgo.depinject.ap.engine.errors.NoFieldSpecifiedInAnnotationConstructorProperties;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.beans.ConstructorProperties;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;

import static kz.greetgo.depinject.ap.engine.Utils.beanConfigToQualifier;
import static kz.greetgo.depinject.ap.engine.Utils.factoredByToQualifier;
import static kz.greetgo.depinject.ap.engine.Utils.findDeclaredField;
import static kz.greetgo.depinject.ap.engine.Utils.noneNull;
import static kz.greetgo.depinject.ap.engine.Utils.typeAsStr;

public class BeanReferencePlace {
  public static Place placeInPublicBeanGetter(Type referencingClass, Class<?> beanClass, Field field) {
    return new Place() {
      @Override
      public PlaceType type() {
        return PlaceType.InPublicBeanGetter;
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

  public static Place placeInConstructorArg(Type referencingType,
                                            Type argType, int argIndex,
                                            Class<?> beanClass,
                                            Constructor<?> constructor) {
    return new Place() {
      @Override
      public PlaceType type() {
        return PlaceType.InConstructorArg;
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

  public static Place placeInBeanFactory(Class<?> beanConfig, BeanConfig beanConfigAnn) {
    return new Place() {
      @Override
      public PlaceType type() {
        return PlaceType.InBeanFactory;
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

  public static Place placeInAnnotationFactoredBy(Class<?> beanClass, FactoredBy factoredBy) {
    return new Place() {
      @Override
      public PlaceType type() {
        return PlaceType.InAnnotationFactoredBy;
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

  public static Place placeInBeanContainerMethod(ExecutableElement method) {
    return new Place() {
      @Override
      public PlaceType type() {
        return PlaceType.InBeanContainerMethod;
      }

      @Override
      public String display() {
        return "bean container method "
          + Utils.asStr((TypeElement) method.getEnclosingElement()) + "." + method.getSimpleName() + "()"
          + qualifierAsStr(qualifier());
      }

      @Override
      public Qualifier qualifier() {
        return noneNull(method.getAnnotation(Qualifier.class));
      }
    };
  }

}
package kz.greetgo.depinject.ap.engine;

import kz.greetgo.depinject.BeanGetter;
import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.ann.HideFromDepinject;
import kz.greetgo.depinject.ann.util.AnnProcUtil;
import kz.greetgo.depinject.ann.util.Place;
import kz.greetgo.depinject.ap.engine.errors.BeanContainerMethodCannotContainAnyArguments;
import kz.greetgo.depinject.ap.engine.errors.IllegalBeanGetterDefinition;
import kz.greetgo.depinject.ap.engine.errors.ManyCandidates;
import kz.greetgo.depinject.ap.engine.errors.MismatchConstructorPropertiesCount;
import kz.greetgo.depinject.ap.engine.errors.NoAnnotationConstructorProperties;
import kz.greetgo.depinject.ap.engine.errors.NoBeanConfig;
import kz.greetgo.depinject.ap.engine.errors.NoCandidates;
import kz.greetgo.depinject.ap.engine.errors.NoConstructorsToCreateBean;
import kz.greetgo.depinject.ap.engine.errors.NoDefaultBeanFactory;
import kz.greetgo.depinject.ap.engine.errors.QualifierNotMatched;
import kz.greetgo.depinject.ap.engine.errors.SuitableConstructorContainsIllegalArgument;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.beans.ConstructorProperties;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.reverseOrder;
import static javax.lang.model.element.Modifier.STATIC;
import static kz.greetgo.depinject.ap.engine.BeanReferencePlace.placeInConstructorArg;
import static kz.greetgo.depinject.ap.engine.BeanReferencePlace.placeInPublicBeanGetter;

public class Context {

  public final BeanConfigTree configTree = new BeanConfigTree();
  public final RoundEnvironment roundEnv;
  public final ProcessingEnvironment processingEnv;

  public Context(RoundEnvironment roundEnv, ProcessingEnvironment processingEnv) {
    this.roundEnv = roundEnv;
    this.processingEnv = processingEnv;
  }

  public BeanContainerManager createManager(TypeElement beanContainerInterface) {
    return new BeanContainerManager(this, beanContainerInterface);
  }

  public BeanCreationCollector newBeanCreationCollector(TypeElement beanContainerInterface) {
    return new BeanCreationCollector(this, beanContainerInterface);
  }

  public NoBeanConfig newNoBeanConfig(Class<?> beanConfig) {
    return new NoBeanConfig(beanConfig);
  }

  public BeanReference newBeanReference(TypeMirror target, Place place) {
    return new BeanReference(this, target, place);
  }

  public BeanCreation newBeanCreationWithBeanFactory(Class<?> parentBeanClass,
                                                     Bean bean,
                                                     BeanReference beanReference) {
    return new BeanCreationWithBeanFactory(this, parentBeanClass, bean, beanReference);
  }

  public NoDefaultBeanFactory newNoDefaultBeanFactory(Class<?> beanClass) {
    return new NoDefaultBeanFactory(beanClass, configTree);
  }

  public NoCandidates newNoCandidates(BeanReference beanReference) {
    return new NoCandidates(beanReference, configTree);
  }

  public RuntimeException newQualifierNotMatched(BeanReference beanReference) {
    return new QualifierNotMatched(beanReference, configTree);
  }

  public ManyCandidates newManyCandidates(BeanReference beanReference) {
    return new ManyCandidates(beanReference, configTree);
  }

  public List<BeanContainerMethod> extractBeanContainerMethodList(TypeElement beanContainer) {
    List<BeanContainerMethod> ret = new ArrayList<>();

    for (ExecutableElement method : AnnProcUtil.extractMethods(beanContainer)) {
      if (method.getModifiers().contains(STATIC)) {
        continue;
      }

      if (method.getParameters().size() > 0) {
        throw new BeanContainerMethodCannotContainAnyArguments(beanContainer, method);
      }

      ret.add(new BeanContainerMethod(this, method));
    }

    Collections.sort(ret);

    return ret;
  }

  public BeanCreationWithFactoryMethod newBeanCreationWithFactoryMethod(Class<?> returnType,
                                                                        Bean bean,
                                                                        BeanCreation parentBeanCreation,
                                                                        Method method) {
    return new BeanCreationWithFactoryMethod(this, returnType, bean, parentBeanCreation, method);
  }

  void fillBeanGetterHolderListInner(List<BeanGetterInPublicField> beanGetterInPublicFieldList, TypeElement beanClass) {

    //TODO pompei continue after will work TypeElement.getFields
    for (Field field : beanClass.getFields()) {
      if (field.getType() == BeanGetter.class) {
        addHolder(beanGetterInPublicFieldList, field, beanClass);
      }
    }

    Collections.sort(beanGetterInPublicFieldList);
  }

  private void addHolder(List<BeanGetterInPublicField> list, Field field, Class<?> beanClass) {

    if (!(field.getGenericType() instanceof ParameterizedType)) {
      throw new IllegalBeanGetterDefinition(beanClass, field.getName());
    }

    ParameterizedType pt = (ParameterizedType) field.getGenericType();

    Type referencingClass = pt.getActualTypeArguments()[0];

    Place place = placeInPublicBeanGetter(referencingClass, beanClass, field);

    BeanReference beanReference = newBeanReference(referencingClass, place);

    list.add(new BeanGetterInPublicField(field.getName(), beanReference));

  }

  public BeanCreation newBeanCreationWithConstructor(Class<?> beanClass, Bean bean) {

    class ArgSet {
      final List<ConstructorArg> argList;
      final Constructor<?> constructor;

      public ArgSet(Constructor<?> constructor, List<ConstructorArg> argList) {
        this.argList = argList;
        this.constructor = constructor;
      }

      public void validateArguments() {
        if (argList.size() > 0) {

          ConstructorProperties cp = constructor.getAnnotation(ConstructorProperties.class);
          if (cp == null) {
            throw new NoAnnotationConstructorProperties(beanClass, constructor);
          }

          if (cp.value().length != argList.size()) {
            throw new MismatchConstructorPropertiesCount(beanClass, cp.value().length, constructor, argList.size());
          }

        }

        {
          int argIndex = 0;
          for (ConstructorArg constructorArg : argList) {
            if (constructorArg.beanReference == null) {
              throw new SuitableConstructorContainsIllegalArgument(argIndex, constructor, beanClass);
            }
            argIndex++;
          }
        }
      }
    }

    List<ArgSet> argSetList = new ArrayList<>();

    for (Constructor<?> constructor : beanClass.getConstructors()) {

      if (constructor.getAnnotation(HideFromDepinject.class) != null) {
        continue;
      }

      List<ConstructorArg> argList = new ArrayList<>();

      int argIndex = 0;
      for (Type argType : constructor.getGenericParameterTypes()) {
        if (!(argType instanceof ParameterizedType)) {
          argList.add(new ConstructorArg(argType, null));
          argIndex++;
          continue;
        }

        ParameterizedType parameterizedType = (ParameterizedType) argType;

        if (parameterizedType.getRawType() != BeanGetter.class) {
          argList.add(new ConstructorArg(argType, null));
          argIndex++;
          continue;
        }

        Type referencingType = parameterizedType.getActualTypeArguments()[0];

        Place place = placeInConstructorArg(referencingType, argType, argIndex, beanClass, constructor);

        BeanReference beanReference = newBeanReference(referencingType, place);

        argList.add(new ConstructorArg(argType, beanReference));

        argIndex++;
      }

      argSetList.add(new ArgSet(constructor, argList));
    }

    if (argSetList.isEmpty()) {
      throw new NoConstructorsToCreateBean(beanClass);
    }

    argSetList.sort(reverseOrder(Comparator.comparing(a -> a.argList.size())));

    argSetList.get(0).validateArguments();

    List<ConstructorArg> selectedArgList = argSetList.get(0).argList;

    return new BeanCreationWithConstructor(this, beanClass, bean, selectedArgList);
  }

  public <T extends Annotation> List<T> getAllAnnotations(TypeElement typeElement, Class<T> annClass) {
    throw new RuntimeException("Impl");
  }
}

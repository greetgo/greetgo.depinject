package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.core.HideFromDepinject;
import kz.greetgo.depinject.core.SkipInject;
import kz.greetgo.depinject.gen.errors.BeanContainerMethodCannotContainAnyArguments;
import kz.greetgo.depinject.gen.errors.IllegalBeanGetterDefinition;
import kz.greetgo.depinject.gen.errors.ManyCandidates;
import kz.greetgo.depinject.gen.errors.NoBeanConfig;
import kz.greetgo.depinject.gen.errors.NoCandidates;
import kz.greetgo.depinject.gen.errors.NoConstructorsToCreateBean;
import kz.greetgo.depinject.gen.errors.NoDefaultBeanFactory;
import kz.greetgo.depinject.gen.errors.NonPublicBeanWithoutConstructor;
import kz.greetgo.depinject.gen.errors.SuitableConstructorContainsIllegalArgument;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static kz.greetgo.depinject.gen.BeanReferencePlace.placeInConstructorArg;
import static kz.greetgo.depinject.gen.BeanReferencePlace.placeInPublicBeanGetter;

public class Context {

  public final BeanConfigTree configTree = new BeanConfigTree();

  public BeanContainerManager createManager(Class<?> beanContainerInterface) {
    return new BeanContainerManager(this, beanContainerInterface);
  }

  public BeanCreationCollector newBeanCreationCollector(Class<?> beanContainerInterface) {
    return new BeanCreationCollector(this, beanContainerInterface);
  }

  public NoBeanConfig newNoBeanConfig(Class<?> beanConfig) {
    return new NoBeanConfig(beanConfig);
  }

  public BeanReference newBeanReference(Type target, BeanReference.Place place) {
    return new BeanReference(this, target, place);
  }

  public BeanCreation newBeanCreationWithBeanFactory(Class<?> parentBeanClass,
                                                     boolean singleton,
                                                     BeanReference beanReference) {
    return new BeanCreationWithBeanFactory(this, parentBeanClass, singleton, beanReference);
  }

  public NoDefaultBeanFactory newNoDefaultBeanFactory(Class<?> beanClass) {
    return new NoDefaultBeanFactory(beanClass, configTree);
  }

  public NoCandidates newNoCandidates(BeanReference beanReference) {
    return new NoCandidates(beanReference, configTree);
  }

  public ManyCandidates newManyCandidates(BeanReference beanReference) {
    return new ManyCandidates(beanReference, configTree);
  }

  public List<BeanContainerMethod> extractBeanContainerMethodList(Class<?> beanContainer) {
    List<BeanContainerMethod> ret = new ArrayList<>();

    for (Method method : beanContainer.getMethods()) {
      if (Modifier.isStatic(method.getModifiers())) {
        continue;
      }

      if (method.getParameterTypes().length > 0) {
        throw new BeanContainerMethodCannotContainAnyArguments(beanContainer, method);
      }

      ret.add(new BeanContainerMethod(this, method));
    }

    Collections.sort(ret);

    return ret;
  }

  public BeanCreationWithFactoryMethod newBeanCreationWithFactoryMethod(Class<?> returnType,
                                                                        boolean singleton,
                                                                        BeanCreation parentBeanCreation,
                                                                        Method method) {
    return new BeanCreationWithFactoryMethod(this, returnType, singleton, parentBeanCreation, method);
  }

  void fillBeanGetterHolderListInner(List<BeanGetterInPublicField> beanGetterInPublicFieldList, Class<?> beanClass) {

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

    BeanReference.Place place = placeInPublicBeanGetter(referencingClass, beanClass, field);

    BeanReference beanReference = newBeanReference(referencingClass, place);

    list.add(new BeanGetterInPublicField(field.getName(), beanReference));
  }

  public BeanCreation newBeanCreationWithConstructor(Class<?> beanClass,
                                                     boolean singleton) {

    class ArgSet {
      final List<ConstructorArg> argList;
      final Constructor<?> constructor;

      public ArgSet(Constructor<?> constructor, List<ConstructorArg> argList) {
        this.argList = argList;
        this.constructor = constructor;
      }

      public void checkArgumentsOk() {
        int argIndex = 0;
        for (ConstructorArg constructorArg : argList) {
          if (constructorArg.beanReference == null) {
            throw new SuitableConstructorContainsIllegalArgument(argIndex, constructor, beanClass);
          }
          argIndex++;
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

        BeanReference.Place place = placeInConstructorArg(referencingType, argType, argIndex, beanClass);

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

    argSetList.get(0).checkArgumentsOk();

    List<ConstructorArg> selectedArgList = argSetList.get(0).argList;
    //checkBeanGetterNotPublicFor(beanClass, selectedArgList);

    return new BeanCreationWithConstructor(this, beanClass, singleton, selectedArgList);
  }


  private static void checkBeanGetterNotPublicFor(Class<?> beanClass, List<ConstructorArg> selectedArgList) {

    Set<String> argTypes = selectedArgList.stream()
        .map(a -> a.argType.toString())
        .collect(Collectors.toSet());

    if (beanClass.getAnnotation(SkipInject.class) != null) {
      return;
    }

    for (Field field : beanClass.getDeclaredFields()) {
      if (Modifier.isPublic(field.getModifiers())) {
        continue;
      }

      if (!BeanGetter.class.equals(field.getType())) {
        continue;
      }

      if (field.getAnnotation(SkipInject.class) != null) {
        continue;
      }

      if (argTypes.contains(field.getGenericType().toString())) {
        continue;
      }

      throw new NonPublicBeanWithoutConstructor(null, field, beanClass);
    }
  }
}

package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.gen.errors.BeanContainerMethodCannotContainAnyArguments;
import kz.greetgo.depinject.gen.errors.IllegalBeanGetterDefinition;
import kz.greetgo.depinject.gen.errors.ManyCandidates;
import kz.greetgo.depinject.gen.errors.NoBeanConfig;
import kz.greetgo.depinject.gen.errors.NoCandidates;
import kz.greetgo.depinject.gen.errors.NoDefaultBeanFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

  public BeanReference newBeanReference(Type target, String place) {
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
        addHolder(beanGetterInPublicFieldList, field.getName(), field.getGenericType(), beanClass);
      }
    }

    Collections.sort(beanGetterInPublicFieldList);
  }

  private void addHolder(List<BeanGetterInPublicField> list, String fieldName, Type beanGetterType, Class<?> beanClass) {
    if (!(beanGetterType instanceof ParameterizedType)) {
      throw new IllegalBeanGetterDefinition(beanClass, fieldName);
    }
    ParameterizedType pt = (ParameterizedType) beanGetterType;
    BeanReference beanReference = newBeanReference(pt.getActualTypeArguments()[0],
        "field " + fieldName + " of " + Utils.asStr(beanClass));
    list.add(new BeanGetterInPublicField(fieldName, beanReference));
  }

  public BeanCreation newBeanCreationWithConstructor(Class<?> beanClass,
                                                     boolean singleton) {

    assert beanClass.getConstructors().length == 1;

    CONSTRUCTOR:
    for (Constructor<?> aConstructor : beanClass.getConstructors()) {

      List<ConstructorArg> argList = new ArrayList<>();

      int argIndex = 0;
      for (Type argType : aConstructor.getGenericParameterTypes()) {
        if (!(argType instanceof ParameterizedType)) {
          continue CONSTRUCTOR;
        }

        ParameterizedType parameterizedType = (ParameterizedType) argType;
        BeanReference beanReference = newBeanReference(parameterizedType.getActualTypeArguments()[0],
            "argument № " + argIndex + " of constructor of " + Utils.asStr(beanClass));

        argList.add(new ConstructorArg(beanReference));

        argIndex++;
      }

      return new BeanCreationWithConstructor(this, beanClass, singleton, argList);
    }

    // TODO: 18.10.18 Сделать здесь правильное сообщение
    throw new RuntimeException("No good constructor");
  }
}

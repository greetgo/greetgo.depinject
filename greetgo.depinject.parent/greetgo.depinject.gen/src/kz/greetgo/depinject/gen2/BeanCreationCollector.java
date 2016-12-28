package kz.greetgo.depinject.gen2;

import kz.greetgo.class_scanner.ClassScannerDef;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.BeanFactoredBy;
import kz.greetgo.depinject.core.BeanFactory;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.errors.FactoryMethodCannotContainAnyArguments;
import kz.greetgo.depinject.gen.errors.NoBeanConfig;
import kz.greetgo.depinject.gen.errors.NoBeanContainer;
import kz.greetgo.depinject.gen.errors.NoDefaultBeanFactory;
import kz.greetgo.depinject.gen.errors.NoInclude;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static kz.greetgo.depinject.gen2.Utils.*;

public class BeanCreationCollector {
  public static List<BeanCreation> collectFrom(Class<?> beanContainerInterface) {
    BeanCreationCollector x = new BeanCreationCollector(beanContainerInterface);
    x.collect();
    Collections.sort(x.ret);
    return x.ret;
  }

  private final Class<?> beanContainerInterface;

  private BeanCreationCollector(Class<?> beanContainerInterface) {
    this.beanContainerInterface = beanContainerInterface;
  }

  private final List<BeanCreation> ret = new ArrayList<>();

  private void collect() {
    if (!BeanContainer.class.isAssignableFrom(beanContainerInterface)) {
      throw new NoBeanContainer(beanContainerInterface);
    }

    List<Include> includes = getAllAnnotations(beanContainerInterface, Include.class);
    if (includes.isEmpty()) throw new NoInclude(beanContainerInterface);

    includes.forEach(this::collectFromInclude);
  }

  private void collectFromInclude(Include include) {
    for (Class<?> beanConfig : include.value()) {
      collectFromBeanConfig(beanConfig);
    }
  }

  private final LinkedList<BeanReference> factoryClassStack = new LinkedList<>();

  private void collectFromBeanConfig(Class<?> beanConfig) {

    BeanConfig beanConfigAnn = beanConfig.getAnnotation(BeanConfig.class);
    if (beanConfigAnn == null) throw new NoBeanConfig(beanConfig);

    Class<? extends BeanFactory> defaultFactoryClass = beanConfigAnn.defaultFactoryClass();

    if (isRealClass(defaultFactoryClass)) {
      factoryClassStack.add(new BeanReference(defaultFactoryClass,
        "default bean factory of " + Utils.asStr(beanConfig)));
    }

    getAllAnnotations(beanConfig, Include.class).forEach(this::collectFromInclude);

    {
      BeanScanner beanScanner = beanConfig.getAnnotation(BeanScanner.class);
      if (beanScanner != null) collectFromPackage(beanConfig.getPackage().getName());
    }

    if (isRealClass(defaultFactoryClass)) {
      factoryClassStack.removeLast();
    }
  }

  private void collectFromPackage(String packageName) {
    new ClassScannerDef().scanPackage(packageName).forEach(someClass -> {
      Bean bean = someClass.getAnnotation(Bean.class);
      if (bean != null) addClassAsBeanAndViewItForAnotherBeans(someClass, bean.singleton());
    });
  }

  private void addClassAsBeanAndViewItForAnotherBeans(Class<?> parentBeanClass,
                                                      boolean singleton
  ) {

    final BeanCreation parentBeanCreation;

    if (isRealClass(parentBeanClass)) {
      ret.add(parentBeanCreation = new BeanCreationWithDefaultConstructor(parentBeanClass, singleton));
    } else {
      ret.add(parentBeanCreation = new BeanCreationWithBeanFactory(
        parentBeanClass, singleton, extractBeanFactoryReference(parentBeanClass)));
    }

    for (Method method : parentBeanClass.getMethods()) {
      Bean bean = getAnnotation(method, Bean.class);
      if (bean == null) continue;
      if (method.getParameterTypes().length > 0) throw new FactoryMethodCannotContainAnyArguments(method);
      ret.add(new BeanCreationWithFactoryMethod(method.getReturnType(), bean.singleton(), parentBeanCreation, method));
    }
  }

  private BeanReference extractBeanFactoryReference(Class<?> beanClass) {
    List<BeanFactoredBy> beanFactoredByList = getAllAnnotations(beanClass, BeanFactoredBy.class);
    if (beanFactoredByList.size() > 0) {
      return new BeanReference(beanFactoredByList.get(0).value(), BeanFactoredBy.class.getSimpleName() +
        " in (or in any parents of) " + Utils.asStr(beanClass));
    }

    if (factoryClassStack.size() == 0) throw new NoDefaultBeanFactory(beanClass);

    return factoryClassStack.getLast();
  }
}

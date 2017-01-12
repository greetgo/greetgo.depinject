package kz.greetgo.depinject.gen2;

import kz.greetgo.class_scanner.ClassScannerDef;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.BeanFactory;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.BeanScannerPackage;
import kz.greetgo.depinject.core.FactoredBy;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.errors.FactoryMethodCannotContainAnyArguments;
import kz.greetgo.depinject.gen.errors.NoBeanConfig;
import kz.greetgo.depinject.gen.errors.NoBeanContainer;
import kz.greetgo.depinject.gen.errors.NoDefaultBeanFactory;
import kz.greetgo.depinject.gen.errors.NoInclude;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static kz.greetgo.depinject.gen2.Utils.*;

public class BeanCreationCollector {
  public static List<BeanCreation> collectFrom(Class<?> beanContainerInterface) {
    BeanCreationCollector x = new BeanCreationCollector(beanContainerInterface);
    x.collect();
    x.ret.sort(Comparator.comparing(o -> o.beanClass.getName()));
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

    boolean addToFactoryClassStack = BeanFactory.class != defaultFactoryClass;

    if (addToFactoryClassStack) {
      factoryClassStack.add(new BeanReference(defaultFactoryClass,
        "default bean factory of " + Utils.asStr(beanConfig)));
    }

    getAllAnnotations(beanConfig, Include.class).forEach(this::collectFromInclude);

    {
      BeanScanner beanScanner = beanConfig.getAnnotation(BeanScanner.class);
      if (beanScanner != null) collectFromPackage(beanConfig.getPackage().getName());
    }

    {
      BeanScannerPackage beanScannerPackage = beanConfig.getAnnotation(BeanScannerPackage.class);
      if (beanScannerPackage != null) for (String subPackageName : beanScannerPackage.value()) {
        collectFromPackage(calcFullName(beanConfig.getPackage().getName(), subPackageName));
      }
    }

    if (addToFactoryClassStack) {
      factoryClassStack.removeLast();
    }
  }

  static String calcFullName(String current, String relative) {
    if (relative.startsWith(".")) return current + relative;
    if (!relative.startsWith("^")) return relative;

    {
      List<String> currentList = new ArrayList<>();
      Collections.addAll(currentList, current.split("\\."));

      int count = 0;
      while (count < relative.length() && relative.charAt(count) == '^') {
        count++;
        if (currentList.size() > 0) {
          currentList.remove(currentList.size() - 1);
        }
      }

      while (count < relative.length() && relative.charAt(count) == '.') count++;

      Collections.addAll(currentList, relative.substring(count).split("\\."));

      return currentList.stream().collect(Collectors.joining("."));
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
    List<FactoredBy> factoredByList = getAllAnnotations(beanClass, FactoredBy.class);
    if (factoredByList.size() > 0) {
      return new BeanReference(factoredByList.get(0).value(), FactoredBy.class.getSimpleName() +
        " in (or in any parents of) " + Utils.asStr(beanClass));
    }

    if (factoryClassStack.size() == 0) throw new NoDefaultBeanFactory(beanClass);

    return factoryClassStack.getLast();
  }
}

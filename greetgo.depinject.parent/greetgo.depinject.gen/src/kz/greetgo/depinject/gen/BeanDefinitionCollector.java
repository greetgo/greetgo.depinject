package kz.greetgo.depinject.gen;

import kz.greetgo.class_scanner.ClassScanner;
import kz.greetgo.class_scanner.ClassScannerDef;
import kz.greetgo.depinject.core.*;
import kz.greetgo.depinject.gen.errors.BeanAlreadyDefined;
import kz.greetgo.depinject.gen.errors.FactoryMethodCannotHaveAnyArguments;
import kz.greetgo.depinject.gen.errors.NoBeanConfig;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class BeanDefinitionCollector {
  private final ClassScanner classScanner = new ClassScannerDef();

  private final Map<Class<?>, BeanDefinition> definitionMap = new HashMap<>();

  public Map<Class<?>, BeanDefinition> getDefinitionMap() {
    Map<Class<?>, BeanDefinition> ret = new HashMap<>();
    ret.putAll(definitionMap);
    return Collections.unmodifiableMap(ret);
  }

  public void appendInclude(Include include) throws Exception {
    for (Class<?> beanConfigClass : include.value()) {
      appendBeanDefinitions(beanConfigClass);
    }
  }

  private final LinkedList<Class<? extends BeanFactory>> defaultsBeanFactories = new LinkedList<>();

  private void appendBeanDefinitions(Class<?> beanConfigClass) throws Exception {
    final BeanConfig beanConfig = beanConfigClass.getAnnotation(BeanConfig.class);
    if (beanConfig == null) throw new NoBeanConfig(beanConfigClass);
    defaultsBeanFactories.addLast(beanConfig.defaultFactoryClass());

    final Include include = beanConfigClass.getAnnotation(Include.class);
    if (include != null) appendInclude(include);

    if (beanConfigClass.getAnnotation(BeanScanner.class) != null) {
      appendPackage(beanConfigClass.getPackage().getName());
    }

    {
      final BeanScannerPackage beanScannerPackage = beanConfigClass.getAnnotation(BeanScannerPackage.class);
      if (beanScannerPackage != null) {
        for (String packageName : beanScannerPackage.value()) {
          appendPackage(packageName);
        }
      }
    }

    defaultsBeanFactories.removeLast();
  }

  private void appendPackage(String packageName) throws Exception {
    for (Class<?> possibleBeanClass : classScanner.scanPackage(packageName)) {
      final Bean bean = possibleBeanClass.getAnnotation(Bean.class);
      final BeanFactoredBy beanFactoredBy = possibleBeanClass.getAnnotation(BeanFactoredBy.class);
      if (bean == null && beanFactoredBy == null) continue;
      boolean singleton = true;
      if (bean != null) singleton = bean.singleton();

      Class<? extends BeanFactory> beanFactory = null;
      if (beanFactoredBy != null) beanFactory = beanFactoredBy.value();

      createAndPutBeanAndItsFactoredBeans(possibleBeanClass, singleton, beanFactory);
    }
  }

  private void createAndPutBeanAndItsFactoredBeans(Class<?> beanClass, boolean singleton,
                                                   Class<? extends BeanFactory> beanFactory) throws Exception {

    Class<?> beanClassFactory = null;
    Method factoryMethod = null;

    if (beanFactory != null) {
      beanClassFactory = beanFactory;
      factoryMethod = beanFactory.getMethod("createBean", Class.class);
    }

    putBeanDefinition(new BeanDefinition(beanClass, singleton, beanClassFactory,
        factoryMethod, defaultsBeanFactories.getLast()));

    for (Method method : beanClass.getMethods()) {
      final Bean bean = method.getAnnotation(Bean.class);
      if (bean == null) continue;
      createAndPutFactoredBean(bean.singleton(), beanClass, method);
    }
  }

  private void putBeanDefinition(BeanDefinition beanDefinition) {
    final BeanDefinition existsBeanDefinition = definitionMap.get(beanDefinition.beanClass);
    if (existsBeanDefinition == null) {
      definitionMap.put(beanDefinition.beanClass, beanDefinition);
      return;
    }

    if (existsBeanDefinition.equals(beanDefinition)) return;

    throw new BeanAlreadyDefined(beanDefinition, existsBeanDefinition);
  }

  private void createAndPutFactoredBean(boolean singleton,
                                        Class<?> factoryBeanClass,
                                        Method factoryMethod) {

    if (factoryMethod.getParameterTypes().length > 0) {
      throw new FactoryMethodCannotHaveAnyArguments(factoryMethod);
    }

    final Class<?> beanClass = factoryMethod.getReturnType();

    final BeanDefinition beanDefinition = new BeanDefinition(beanClass, singleton, factoryBeanClass,
        factoryMethod, defaultsBeanFactories.getLast());

    putBeanDefinition(beanDefinition);
  }

  public static boolean showDefinitionMap = false;

  public void showDefinitionMap() {
    if (!showDefinitionMap) return;
    for (Map.Entry<Class<?>, BeanDefinition> e : definitionMap.entrySet()) {
      System.out.println(e.getKey().getSimpleName() + " -> " + e.getValue());
    }
  }
}

package kz.greetgo.depinject.gen;

import kz.greetgo.class_scanner.ClassScannerDef;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.BeanFactory;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.FactoredBy;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.core.ScanPackage;
import kz.greetgo.depinject.gen.errors.FactoryMethodCannotContainAnyArguments;
import kz.greetgo.depinject.gen.errors.NoBeanContainer;
import kz.greetgo.depinject.gen.errors.NoInclude;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static kz.greetgo.depinject.gen.BeanReferencePlace.placeInAnnotationFactoredBy;
import static kz.greetgo.depinject.gen.BeanReferencePlace.placeInBeanFactory;


public class BeanCreationCollector {

  private final Context context;

  private final Class<?> beanContainerInterface;

  public BeanCreationCollector(Context context, Class<?> beanContainerInterface) {
    this.context = context;
    this.beanContainerInterface = beanContainerInterface;
  }

  public final List<BeanCreation> beanCreationList = new ArrayList<>();

  public List<BeanCreation> collect() {
    if (!BeanContainer.class.isAssignableFrom(beanContainerInterface)) {
      throw new NoBeanContainer(beanContainerInterface);
    }

    context.configTree.root(beanContainerInterface.getName());

    List<Include> includes = Utils.getAllAnnotations(beanContainerInterface, Include.class);
    if (includes.isEmpty()) {
      throw new NoInclude(beanContainerInterface);
    }

    includes.forEach(this::collectFromInclude);

    return beanCreationList;
  }

  private void collectFromInclude(Include include) {
    //tab++;
    for (Class<?> beanConfig : include.value()) {
      context.configTree.includes(beanConfig.getName());
      collectFromBeanConfig(beanConfig);
    }
    //tab--;
  }

  private final LinkedList<BeanReference> factoryClassStack = new LinkedList<>();

  private void collectFromBeanConfig(Class<?> beanConfig) {
    try {
      context.configTree.tab++;

      BeanConfig beanConfigAnn = beanConfig.getAnnotation(BeanConfig.class);
      if (beanConfigAnn == null) {
        throw context.newNoBeanConfig(beanConfig);
      }

      Class<? extends BeanFactory> factoryClass = beanConfigAnn.factory();

      boolean addToFactoryClassStack = BeanFactory.class != factoryClass;

      if (addToFactoryClassStack) {

        BeanReference.Place place = placeInBeanFactory(beanConfig, beanConfigAnn);

        factoryClassStack.add(context.newBeanReference(factoryClass, place));
      }

      Utils.getAllAnnotations(beanConfig, Include.class).forEach(this::collectFromInclude);

      {
        BeanScanner beanScanner = beanConfig.getAnnotation(BeanScanner.class);
        if (beanScanner != null) {
          collectFromPackage(beanConfig.getPackage().getName());
        }
      }

      {
        //noinspection deprecation
        ScanPackage scanPackage = beanConfig.getAnnotation(ScanPackage.class);

        if (scanPackage != null) {
          for (String subPackageName : scanPackage.value()) {
            String packageName = calcFullName(beanConfig.getPackage().getName(), subPackageName);
            context.configTree.scannerPackage(packageName);
            context.configTree.tab++;
            collectFromPackage(packageName);
            context.configTree.tab--;
          }
        }
      }

      if (addToFactoryClassStack) {
        factoryClassStack.removeLast();
      }

    } finally {
      context.configTree.tab--;
    }
  }


  static String calcFullName(String current, String relative) {
    if (relative.startsWith(".")) {
      return current + relative;
    }
    if (!relative.startsWith("^")) {
      return relative;
    }

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

      while (count < relative.length() && relative.charAt(count) == '.') {
        count++;
      }

      Collections.addAll(currentList, relative.substring(count).split("\\."));

      return String.join(".", currentList);
    }
  }

  private void collectFromPackage(String packageName) {
    new ClassScannerDef().scanPackage(packageName).forEach(someClass -> {
      Bean bean = someClass.getAnnotation(Bean.class);
      if (bean != null) {
        addClassAsBeanAndViewItForAnotherBeans(someClass, bean);
      }
    });
  }

  private void addClassAsBeanAndViewItForAnotherBeans(Class<?> beanClass, Bean bean) {
    final BeanCreation beanCreation;

    if (Utils.isRealClass(beanClass)) {
      beanCreationList.add(beanCreation =
          context.newBeanCreationWithConstructor(beanClass, bean));
    } else {
      beanCreationList.add(beanCreation =
          context.newBeanCreationWithBeanFactory(beanClass, bean, extractBeanFactoryReference(beanClass)));
    }

    context.configTree.bean("" + beanCreation);
    context.configTree.tab++;

    for (Method method : beanClass.getMethods()) {

      Bean methodBean = Utils.getAnnotation(method, Bean.class);
      if (methodBean == null) {
        continue;
      }

      if (method.getParameterTypes().length > 0) {
        throw new FactoryMethodCannotContainAnyArguments(method);
      }

      BeanCreationWithFactoryMethod subBean = context.newBeanCreationWithFactoryMethod(
          method.getReturnType(), methodBean, beanCreation, method);

      context.configTree.bean("" + subBean);
      beanCreationList.add(subBean);
    }

    context.configTree.tab--;
  }

  private BeanReference extractBeanFactoryReference(Class<?> beanClass) {
    List<FactoredBy> factoredByList = Utils.getAllAnnotations(beanClass, FactoredBy.class);

    if (factoredByList.size() > 0) {

      FactoredBy factoredBy = factoredByList.get(0);

      BeanReference.Place place = placeInAnnotationFactoredBy(beanClass, factoredBy);


      return context.newBeanReference(factoredBy.value(), place);
    }

    if (factoryClassStack.size() == 0) {
      throw context.newNoDefaultBeanFactory(beanClass);
    }

    return factoryClassStack.getLast();
  }
}

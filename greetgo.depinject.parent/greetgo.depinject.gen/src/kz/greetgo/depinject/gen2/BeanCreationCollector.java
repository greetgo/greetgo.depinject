package kz.greetgo.depinject.gen2;

import kz.greetgo.class_scanner.ClassScannerDef;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.errors.FactoryMethodCannotHaveAnyArguments;
import kz.greetgo.depinject.gen.errors.NoBeanConfig;
import kz.greetgo.depinject.gen.errors.NoBeanContainer;
import kz.greetgo.depinject.gen.errors.NoInclude;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static kz.greetgo.depinject.gen2.Utils.getAllAnnotations;
import static kz.greetgo.depinject.gen2.Utils.getAnnotation;

public class BeanCreationCollector {
  public static List<BeanCreation> collectFrom(Class<?> beanContainerInterface) {
    if (!BeanContainer.class.isAssignableFrom(beanContainerInterface)) {
      throw new NoBeanContainer(beanContainerInterface);
    }

    List<Include> includes = getAllAnnotations(beanContainerInterface, Include.class);
    if (includes.isEmpty()) throw new NoInclude(beanContainerInterface);

    List<BeanCreation> ret = new ArrayList<>();
    includes.forEach(include -> collectFromInclude(ret, include));
    return ret;
  }

  private static void collectFromInclude(List<BeanCreation> ret, Include include) {
    for (Class<?> beanConfig : include.value()) {
      collectBeanConfig(ret, beanConfig);
    }
  }

  private static void collectBeanConfig(List<BeanCreation> ret, Class<?> beanConfig) {

    BeanConfig beanConfigAnn = beanConfig.getAnnotation(BeanConfig.class);
    if (beanConfigAnn == null) throw new NoBeanConfig(beanConfig);

    getAllAnnotations(beanConfig, Include.class).forEach(include -> collectFromInclude(ret, include));

    {
      BeanScanner beanScanner = beanConfig.getAnnotation(BeanScanner.class);
      if (beanScanner != null) collectFromPackage(ret, beanConfig.getPackage().getName());
    }
  }

  private static void collectFromPackage(List<BeanCreation> ret, String packageName) {
    new ClassScannerDef().scanPackage(packageName).forEach(someClass -> {
      Bean bean = someClass.getAnnotation(Bean.class);
      if (bean != null) addClassAsBeanAndViewItOnAnotherBeans(ret, someClass, bean.singleton());
    });
  }

  private static void addClassAsBeanAndViewItOnAnotherBeans(List<BeanCreation> ret,
                                                            Class<?> parentBeanClass,
                                                            boolean singleton
  ) {

    //boolean isInterface = parentBeanClass.isInterface();
    //boolean isAbstract = Modifier.isAbstract(parentBeanClass.getModifiers());

    final BeanCreation parentBeanCreation;

    //if (!isInterface && !isAbstract) {
    ret.add(parentBeanCreation = new BeanCreationWithDefaultConstructor(parentBeanClass, singleton));
    //}

    for (Method method : parentBeanClass.getMethods()) {
      Bean bean = getAnnotation(method, Bean.class);
      if (bean == null) continue;
      if (method.getParameterTypes().length > 0) throw new FactoryMethodCannotHaveAnyArguments(method);
      ret.add(new BeanCreationWithFactoryMethod(method.getReturnType(), bean.singleton(), parentBeanCreation, method));
    }
  }
}

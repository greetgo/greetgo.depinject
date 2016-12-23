package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.errors.NoBeanContainer;
import kz.greetgo.depinject.gen.errors.NoInclude;

import java.util.List;

public class BeanCreationCollector {
  public static List<BeanCreation> collect(Class<?> beanContainerInterface) {
    if (!BeanContainer.class.isAssignableFrom(beanContainerInterface)) {
      throw new NoBeanContainer(beanContainerInterface);
    }

    Include include = Utils.getAnnotation(beanContainerInterface, Include.class);
    if (include == null) throw new NoInclude(beanContainerInterface);

    return null;
  }
}

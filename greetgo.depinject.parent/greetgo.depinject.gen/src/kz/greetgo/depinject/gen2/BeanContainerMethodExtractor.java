package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.gen.errors.BeanContainerMethodCannotContainAnyArguments;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeanContainerMethodExtractor {

  public static List<BeanContainerMethod> extract(Class<?> beanContainer) {
    List<BeanContainerMethod> ret = new ArrayList<>();

    for (Method method : beanContainer.getMethods()) {
      if (method.getParameterTypes().length > 0) {
        throw new BeanContainerMethodCannotContainAnyArguments(beanContainer, method);
      }

      ret.add(new BeanContainerMethod(method));
    }

    Collections.sort(ret);

    return ret;
  }

}

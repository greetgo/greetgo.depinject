package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.core.replace.ReplaceInstanceOf;
import kz.greetgo.depinject.core.replace.ReplaceWithAnn;

public class ReplaceCheckerExtractor {
  public static ReplaceChecker fromBeanClass(Class<?> beanClass) {

    ReplaceChecker checker1 = extractWithAnn(beanClass);
    ReplaceChecker checker2 = extractInstanceOf(beanClass);

    if (checker1 == null && checker2 == null) return c -> false;

    if (checker1 == null) return checker2;
    if (checker2 == null) return checker1;

    return c -> checker1.check(c) && checker2.check(c);
  }

  private static ReplaceChecker extractInstanceOf(Class<?> beanClass) {
    ReplaceInstanceOf ann = beanClass.getAnnotation(ReplaceInstanceOf.class);
    if (ann == null) return null;
    if (ann.value().length == 0) return null;
    return c -> {
      for (Class<?> aClass : ann.value()) {
        if (aClass.isAssignableFrom(c)) return true;
      }
      return false;
    };
  }

  private static ReplaceChecker extractWithAnn(Class<?> beanClass) {
    ReplaceWithAnn ann = beanClass.getAnnotation(ReplaceWithAnn.class);
    if (ann == null) return null;
    return c -> c.getAnnotation(ann.value()) != null;
  }
}

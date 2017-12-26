package kz.greetgo.depinject.gen.errors;

import kz.greetgo.depinject.gen.BeanConfigTree;

public class NoDefaultBeanFactory extends RuntimeException {

  public final Class<?> beanClass;
  public final BeanConfigTree configTree;

  public NoDefaultBeanFactory(Class<?> beanClass, BeanConfigTree configTree) {
    super("For " + beanClass + configTree.asStr(false, true));
    this.beanClass = beanClass;
    this.configTree = configTree;
  }
}

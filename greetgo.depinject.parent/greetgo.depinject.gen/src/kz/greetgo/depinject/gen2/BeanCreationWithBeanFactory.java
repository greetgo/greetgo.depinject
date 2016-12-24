package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.core.BeanFactory;

public class BeanCreationWithBeanFactory extends BeanCreation {
  public final BeanReference beanFactorySource;

  public BeanCreationWithBeanFactory(Class<?> beanClass, boolean singleton, BeanReference beanFactorySource) {
    super(beanClass, singleton);
    if (!BeanFactory.class.isAssignableFrom(beanFactorySource.targetClass())) {
      throw new RuntimeException(beanFactorySource.target + " is not bean factory");
    }
    this.beanFactorySource = beanFactorySource;
  }
}

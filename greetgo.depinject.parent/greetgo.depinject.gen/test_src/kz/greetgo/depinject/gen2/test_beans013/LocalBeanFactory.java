package kz.greetgo.depinject.gen2.test_beans013;

import kz.greetgo.depinject.core.BeanFactory;

public abstract class LocalBeanFactory implements BeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) throws Exception {
    return abstractCreateBean(beanClass);
  }

  protected abstract Object abstractCreateBean(Class<?> beanClass) throws IllegalAccessException, InstantiationException;
}

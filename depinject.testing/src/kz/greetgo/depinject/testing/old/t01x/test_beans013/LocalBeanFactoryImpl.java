package kz.greetgo.depinject.testing.old.t01x.test_beans013;

import kz.greetgo.depinject.ann.Bean;

@Bean
public class LocalBeanFactoryImpl extends LocalBeanFactory {
  @Override
  protected Object abstractCreateBean(Class<?> beanClass) throws IllegalAccessException, InstantiationException {
    return beanClass.newInstance();
  }
}

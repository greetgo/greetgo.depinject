package kz.greetgo.depinject.gen2.test_beans012;

import kz.greetgo.depinject.core.Bean;

@Bean
public class LocalBeanFactoryImpl implements LocalBeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) throws Exception {
    return beanClass.newInstance();
  }
}

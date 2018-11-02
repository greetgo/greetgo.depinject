package kz.greetgo.depinject.gen.t01x.test_beans012;

import kz.greetgo.depinject.core.Bean;

@Bean
public class LocalBeanFactoryImpl implements LocalBeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) throws Exception {
    return beanClass.newInstance();
  }
}

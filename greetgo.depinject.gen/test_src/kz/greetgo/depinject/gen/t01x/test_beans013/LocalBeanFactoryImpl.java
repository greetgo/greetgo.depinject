package kz.greetgo.depinject.gen.t01x.test_beans013;

import kz.greetgo.depinject.core.Bean;

@Bean
public class LocalBeanFactoryImpl extends LocalBeanFactory {
  @Override
  protected Object abstractCreateBean(Class<?> beanClass) throws IllegalAccessException, InstantiationException {
    return beanClass.newInstance();
  }
}

package kz.greetgo.depinject.testing.old.t00x.test_beans005.top;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.ann.BeanFactory;

@Bean
public class TopBeanFactory implements BeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) {
    return null;
  }
}

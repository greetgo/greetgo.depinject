package kz.greetgo.depinject.testing.old.t00x.test_beans005.sub_beans_2;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.ann.BeanFactory;

@Bean
public class BeanFactory2 implements BeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) {
    return null;
  }
}

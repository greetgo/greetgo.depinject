package kz.greetgo.depinject.testing.old.t00x.test_beans005.sub_beans_1;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.ann.BeanFactory;

@Bean
public class BeanFactory1 implements BeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) {
    return null;
  }
}

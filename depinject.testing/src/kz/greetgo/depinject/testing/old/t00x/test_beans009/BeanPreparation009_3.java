package kz.greetgo.depinject.testing.old.t00x.test_beans009;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanPreparation;

@Bean
public class BeanPreparation009_3 implements BeanPreparation<Bean3> {
  @Override
  public Bean3 prepareBean(Bean3 bean) {
    return bean;
  }
}

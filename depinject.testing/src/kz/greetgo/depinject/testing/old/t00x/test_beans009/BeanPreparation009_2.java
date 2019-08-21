package kz.greetgo.depinject.testing.old.t00x.test_beans009;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanPreparation;

@Bean
public class BeanPreparation009_2 implements BeanPreparation<Bean2> {
  @Override
  public Bean2 prepareBean(Bean2 bean) {
    return bean;
  }
}

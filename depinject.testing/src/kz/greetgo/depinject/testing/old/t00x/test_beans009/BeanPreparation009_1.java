package kz.greetgo.depinject.testing.old.t00x.test_beans009;

import kz.greetgo.depinject.ann.Bean;

@SuppressWarnings("deprecation")
@Bean
@kz.greetgo.depinject.ann.BeanPreparationPriority(-10)
public class BeanPreparation009_1 implements kz.greetgo.depinject.BeanPreparation<Bean1> {
  @Override
  public Bean1 prepareBean(Bean1 bean) {
    return bean;
  }
}

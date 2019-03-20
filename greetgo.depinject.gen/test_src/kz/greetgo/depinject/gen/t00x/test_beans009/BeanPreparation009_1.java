package kz.greetgo.depinject.gen.t00x.test_beans009;

import kz.greetgo.depinject.core.Bean;

@SuppressWarnings("deprecation")
@Bean
@kz.greetgo.depinject.core.BeanPreparationPriority(-10)
public class BeanPreparation009_1 implements kz.greetgo.depinject.core.BeanPreparation<Bean1> {
  @Override
  public Bean1 prepareBean(Bean1 bean) {
    return bean;
  }
}

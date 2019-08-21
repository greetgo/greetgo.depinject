package kz.greetgo.depinject.testing.old.t00x.test_beans009;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanPreparation;
import kz.greetgo.depinject.ann.BeanPreparationPriority;

@SuppressWarnings("deprecation")
@Bean
@BeanPreparationPriority(-9)
public class BeanPreparation009_1_a implements BeanPreparation<Bean1> {
  @Override
  public Bean1 prepareBean(Bean1 bean) {
    return bean;
  }
}

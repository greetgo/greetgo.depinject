package kz.greetgo.depinject.testing.old.t00x.test_beans008;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanPreparation;
import kz.greetgo.depinject.ann.BeanPreparationPriority;

@Bean
@BeanPreparationPriority(10)
public class BeanPreparation008_1 implements BeanPreparation<Bean1> {
  @Override
  public Bean1 prepareBean(Bean1 bean) {
    return bean;
  }
}

package kz.greetgo.depinject.gen.t00x.test_beans008;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanPreparation;
import kz.greetgo.depinject.core.BeanPreparationPriority;

@Bean
@BeanPreparationPriority(10)
public class BeanPreparation008_1 implements BeanPreparation<Bean1> {
  @Override
  public Bean1 prepareBean(Bean1 bean) {
    return bean;
  }
}

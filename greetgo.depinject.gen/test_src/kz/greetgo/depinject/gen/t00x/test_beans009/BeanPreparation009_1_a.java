package kz.greetgo.depinject.gen.t00x.test_beans009;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanPreparation;
import kz.greetgo.depinject.core.BeanPreparationPriority;

@SuppressWarnings("deprecation")
@Bean
@BeanPreparationPriority(-9)
public class BeanPreparation009_1_a implements BeanPreparation<Bean1> {
  @Override
  public Bean1 prepareBean(Bean1 bean) {
    return bean;
  }
}

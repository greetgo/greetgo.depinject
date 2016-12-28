package kz.greetgo.depinject.gen2.test_beans009;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanPreparation;

@Bean
public class BeanPreparation009_2 implements BeanPreparation<Bean2> {
  @Override
  public Bean2 prepareBean(Bean2 bean) {
    return bean;
  }
}

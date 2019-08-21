package kz.greetgo.depinject.testing.old.t01x.test_beans010;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanPreparation;

@SuppressWarnings("deprecation")
@Bean
public class Preparation_BeanA2 implements BeanPreparation<BeanA2> {
  @Override
  public BeanA2 prepareBean(BeanA2 bean) {
    return bean;
  }
}

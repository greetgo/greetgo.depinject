package kz.greetgo.depinject.testing.old.t01x.test_beans010;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanPreparation;

@SuppressWarnings("deprecation")
@Bean
public class Preparation_BeanA1 implements BeanPreparation<BeanA1> {
  @Override
  public BeanA1 prepareBean(BeanA1 bean) {
    return bean;
  }
}

package kz.greetgo.depinject.testing.old.t01x.test_beans011;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanPreparation;

@Bean
public class Preparation_BeanA3 implements BeanPreparation<BeanA3> {
  @Override
  public BeanA3 prepareBean(BeanA3 bean) {
    return bean;
  }
}

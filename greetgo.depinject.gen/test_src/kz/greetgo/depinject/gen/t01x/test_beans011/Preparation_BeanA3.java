package kz.greetgo.depinject.gen.t01x.test_beans011;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanPreparation;

@Bean
public class Preparation_BeanA3 implements BeanPreparation<BeanA3> {
  @Override
  public BeanA3 prepareBean(BeanA3 bean) {
    return bean;
  }
}

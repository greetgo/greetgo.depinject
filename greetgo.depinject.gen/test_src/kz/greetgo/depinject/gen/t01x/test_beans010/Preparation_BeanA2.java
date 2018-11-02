package kz.greetgo.depinject.gen.t01x.test_beans010;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanPreparation;

@Bean
public class Preparation_BeanA2 implements BeanPreparation<BeanA2> {
  @Override
  public BeanA2 prepareBean(BeanA2 bean) {
    return bean;
  }
}

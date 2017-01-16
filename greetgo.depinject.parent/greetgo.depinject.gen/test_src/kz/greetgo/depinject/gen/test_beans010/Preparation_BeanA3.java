package kz.greetgo.depinject.gen.test_beans010;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanPreparation;

@Bean
public class Preparation_BeanA3 implements BeanPreparation<BeanA3> {
  @Override
  public BeanA3 prepareBean(BeanA3 bean) {
    return bean;
  }
}

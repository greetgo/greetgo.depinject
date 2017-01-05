package kz.greetgo.depinject.gen2.test_beans010;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanPreparation;

@Bean
public class Preparation_BeanA1 implements BeanPreparation<BeanA1> {
  @Override
  public BeanA1 prepareBean(BeanA1 bean) {
    return bean;
  }
}

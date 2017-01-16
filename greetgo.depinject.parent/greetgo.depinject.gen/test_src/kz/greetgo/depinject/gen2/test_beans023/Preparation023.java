package kz.greetgo.depinject.gen2.test_beans023;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanPreparation;

@Bean
public class Preparation023 implements BeanPreparation<Iface023> {
  @Override
  public Iface023 prepareBean(Iface023 bean) {
    return bean;
  }
}

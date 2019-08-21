package kz.greetgo.depinject.testing.old.t02x.test_beans023;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanPreparation;

@Bean
public class Preparation023 implements BeanPreparation<Iface023> {
  @Override
  public Iface023 prepareBean(Iface023 bean) {
    return bean;
  }
}

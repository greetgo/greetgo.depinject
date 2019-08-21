package kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.groupA;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.BeanPreparation;

@Bean
public class Transactor implements BeanPreparation<Transactional> {
  @Override
  public Transactional prepareBean(Transactional bean) {
    System.out.println("Transacting bean " + bean);
    return bean;
  }
}

package kz.greetgo.depinject.gen.beans.groupA;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanPreparation;

@Bean
public class Transactor implements BeanPreparation<Transactional> {
  @Override
  public Transactional prepareBean(Transactional bean) {
    System.out.println("Transacting bean " + bean);
    return bean;
  }
}

package kz.greetgo.depinject.core;

public interface BeanPreparation<BeanType> {
  BeanType prepareBean(BeanType bean);
}

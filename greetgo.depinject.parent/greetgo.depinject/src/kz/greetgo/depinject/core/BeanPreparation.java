package kz.greetgo.depinject.core;

@SuppressWarnings("unused")
public interface BeanPreparation<BeanType> {
  BeanType prepareBean(BeanType bean);
}

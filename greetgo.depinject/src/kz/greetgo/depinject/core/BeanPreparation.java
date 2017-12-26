package kz.greetgo.depinject.core;

@Deprecated
@SuppressWarnings("unused")
public interface BeanPreparation<BeanType> {
  BeanType prepareBean(BeanType bean);
}

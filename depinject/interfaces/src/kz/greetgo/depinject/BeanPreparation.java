package kz.greetgo.depinject;

@Deprecated
@SuppressWarnings("unused")
public interface BeanPreparation<BeanType> {
  BeanType prepareBean(BeanType bean);
}

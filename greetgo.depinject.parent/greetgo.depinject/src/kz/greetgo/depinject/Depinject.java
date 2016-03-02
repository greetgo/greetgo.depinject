package kz.greetgo.depinject;

import kz.greetgo.depinject.core.BeanContainer;

public class Depinject {
  public static <T extends BeanContainer> T newInstance(Class<T> tClass) {
    try {
      //noinspection unchecked
      return (T) Class.forName(tClass.getName() + BeanContainer.IMPL_POSTFIX).newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      throw new NoImplementor(e.getMessage(), e);
    }
  }
}

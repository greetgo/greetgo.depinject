package kz.greetgo.depinject;

import kz.greetgo.depinject.core.BeanContainer;

public class Depinject {
  public static <T extends BeanContainer> T newInstance(Class<T> tClass) {
    //noinspection unchecked
    try {
      return (T) Class.forName(tClass.getName() + BeanContainer.IMPL_POSTFIX).newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}

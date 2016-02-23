package kz.greetgo.depinject;

import kz.greetgo.depinject.core.BeanContainer;

public class Depinject {
  public static <T extends BeanContainer> T newInstance(Class<T> tClass) throws Exception, InstantiationException {
    //noinspection unchecked
    return (T) Class.forName(tClass.getName() + BeanContainer.IMPL_POSTFIX).newInstance();
  }
}

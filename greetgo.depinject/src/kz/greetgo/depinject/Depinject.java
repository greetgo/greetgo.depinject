package kz.greetgo.depinject;

import kz.greetgo.depinject.core.BeanContainer;

import java.util.concurrent.atomic.AtomicReference;

public class Depinject {
  public static <T extends BeanContainer> T newInstance(Class<T> tClass) {

    String className = tClass.getName() + BeanContainer.IMPL_POSTFIX;

    try {

      try {

        //noinspection unchecked
        return (T) Class.forName(className).newInstance();

      } catch (ClassNotFoundException e) {

        ClassLoader classLoader = additionalLoader.get();
        if (classLoader == null) {
          throw new NoImplementor(e.getMessage(), e);
        }

        try {

          //noinspection unchecked
          return (T) classLoader.loadClass(className).newInstance();

        } catch (ClassNotFoundException e1) {
          throw new NoImplementor(e1.getMessage(), e1);
        }
      }

    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }

  }

  public static final AtomicReference<ClassLoader> additionalLoader = new AtomicReference<>(null);

}

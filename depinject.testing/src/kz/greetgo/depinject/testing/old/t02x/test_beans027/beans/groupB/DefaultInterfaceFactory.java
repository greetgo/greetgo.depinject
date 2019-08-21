package kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.groupB;

import kz.greetgo.depinject.ann.BeanFactory;
import kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.using_default_factory.CreatingByDefaultFactory;

public abstract class DefaultInterfaceFactory implements BeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) {
    if (beanClass == CreatingByDefaultFactory.class) {
      //noinspection Convert2Lambda
      return new CreatingByDefaultFactory() {
        @Override
        public String hello() {
          return "Hello from CreatingByDefaultFactory";
        }
      };
    }
    throw new RuntimeException("Cannot create " + beanClass);
  }
}

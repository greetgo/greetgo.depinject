package kz.greetgo.depinject.gen.beans.groupB;

import kz.greetgo.depinject.core.BeanFactory;
import kz.greetgo.depinject.gen.beans.using_default_factory.CreatingByDefaultFactory;

public abstract class DefaultInterfaceFactory implements BeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) {
    if (beanClass == CreatingByDefaultFactory.class) {
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

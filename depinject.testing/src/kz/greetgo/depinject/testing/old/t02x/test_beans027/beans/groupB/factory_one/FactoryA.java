package kz.greetgo.depinject.testing.old.t02x.test_beans027.beans.groupB.factory_one;

import kz.greetgo.depinject.ann.Bean;
import kz.greetgo.depinject.ann.BeanFactory;

@Bean
public class FactoryA implements BeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) {
    if (InterfaceCreatingWithA.class.isAssignableFrom(beanClass)) {
      return new InterfaceCreatingWithA() {
        @Override
        public String helloFromInterfaceCreatingWithA() {
          return "Hello from interface creating with A";
        }
      };
    }
    if (AnotherInterfaceCreatingWithA.class.isAssignableFrom(beanClass)) {
      return new AnotherInterfaceCreatingWithA() {
        @Override
        public String helloFromAnotherInterfaceCreatingWithA() {
          return "Hello from another interface creating with A";
        }
      };
    }
    throw new IllegalArgumentException("Cannot factory " + beanClass);
  }
}

package kz.greetgo.depinject.testng.complex_tests.UsingBeanFactory.beans.core;

import kz.greetgo.depinject.core.BeanFactory;
import kz.greetgo.depinject.testng.complex_tests.UsingBeanFactory.util.UsingBeanFactory;

public abstract class LocalBeanFactory implements BeanFactory {

  protected abstract int nextWindowNumber();

  protected abstract int nextComputerNumber();

  @Override
  public Object createBean(Class<?> beanClass) {

    if (beanClass == UsingBeanFactoryWindow.class) return new UsingBeanFactoryWindow() {
      final int myNumber = nextWindowNumber();

      @Override
      public void lookOut() {
        UsingBeanFactory.log.add("Look out window " + myNumber);
      }
    };

    if (beanClass == UsingBeanFactoryComputer.class) return new UsingBeanFactoryComputer() {
      final int myNumber = nextComputerNumber();

      @Override
      public void turnOn() {
        UsingBeanFactory.log.add("Turn on computer " + myNumber);
      }
    };

    throw new RuntimeException("Unknown " + beanClass);
  }
}

package kz.greetgo.depinject.testng.complex_tests.UsingFactoryBean.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanFactory;
import kz.greetgo.depinject.testng.complex_tests.UsingFactoryBean.UsingBeanFactoryTest;

@Bean
public class LocalBeanFactory implements BeanFactory {

  int windowNumber = 1;
  int computerNumber = 1;

  @Override
  public Object createBean(Class<?> beanClass) {

    if (beanClass == UsingBeanFactoryWindow.class) return new UsingBeanFactoryWindow() {
      final int myNumber = windowNumber++;

      @Override
      public void lookOut() {
        UsingBeanFactoryTest.log.add("Look out window " + myNumber);
      }
    };

    if (beanClass == UsingBeanFactoryComputer.class) return new UsingBeanFactoryComputer() {
      final int myNumber = computerNumber++;

      @Override
      public void turnOn() {
        UsingBeanFactoryTest.log.add("Turn on computer " + myNumber);
      }
    };

    throw new RuntimeException("Unknown " + beanClass);
  }
}

package kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchySequence.beans2;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanFactory;
import kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchySequence.BeanFactoryHierarchySequenceTest;

@Bean
public class BeanFactoryParallelForWindow2 implements BeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) {

    if (beanClass == BeanFactoryHierarchySequenceWindow2.class) return (BeanFactoryHierarchySequenceWindow2)
      () -> BeanFactoryHierarchySequenceTest.log.add("Look out window 2");

    throw new RuntimeException("Unknown " + beanClass);

  }
}

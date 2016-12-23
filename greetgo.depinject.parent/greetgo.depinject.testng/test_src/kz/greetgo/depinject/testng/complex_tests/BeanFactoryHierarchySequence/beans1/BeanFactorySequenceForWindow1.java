package kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchySequence.beans1;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanFactory;
import kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchySequence.BeanFactoryHierarchySequenceTest;

@Bean
public class BeanFactorySequenceForWindow1 implements BeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) {

    if (beanClass == BeanFactoryHierarchySequenceWindow1.class) return (BeanFactoryHierarchySequenceWindow1)
      () -> BeanFactoryHierarchySequenceTest.log.add("Look out window 1");

    throw new RuntimeException("Unknown " + beanClass);

  }
}

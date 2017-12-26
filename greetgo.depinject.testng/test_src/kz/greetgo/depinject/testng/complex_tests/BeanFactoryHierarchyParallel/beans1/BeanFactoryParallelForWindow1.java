package kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchyParallel.beans1;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanFactory;
import kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchyParallel.BeanFactoryHierarchyParallelTest;

@Bean
public class BeanFactoryParallelForWindow1 implements BeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) {

    if (beanClass == BeanFactoryHierarchyParallelWindow1.class) return (BeanFactoryHierarchyParallelWindow1)
      () -> BeanFactoryHierarchyParallelTest.log.add("Look out window 1");

    throw new RuntimeException("Unknown " + beanClass);

  }
}

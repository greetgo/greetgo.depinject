package kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchyParallel.beans2;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanFactory;
import kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchyParallel.BeanFactoryHierarchyParallelTest;

@Bean
public class BeanFactoryParallelForWindow2 implements BeanFactory {
  @Override
  public Object createBean(Class<?> beanClass) {

    if (beanClass == BeanFactoryHierarchyParallelWindow2.class) return (BeanFactoryHierarchyParallelWindow2)
      () -> BeanFactoryHierarchyParallelTest.log.add("Look out window 2");

    throw new RuntimeException("Unknown " + beanClass);

  }
}

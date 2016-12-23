package kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchyParallel.beans1;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;

@BeanConfig(defaultFactoryClass = BeanFactoryParallelForWindow1.class)
@BeanScanner
public class BeanConfigBeanFactoryHierarchyParallel1 {
}

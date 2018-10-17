package kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchySequence.beans2;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;

@BeanConfig(factory = BeanFactoryParallelForWindow2.class)
@BeanScanner
public class BeanConfigBeanFactoryHierarchySequence2 {
}

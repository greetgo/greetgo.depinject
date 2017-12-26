package kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchySequence.beans1;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchySequence.beans2.BeanConfigBeanFactoryHierarchySequence2;

@BeanConfig(defaultFactoryClass = BeanFactorySequenceForWindow1.class)
@BeanScanner
@Include(BeanConfigBeanFactoryHierarchySequence2.class)
public class BeanConfigBeanFactoryHierarchySequence1 {
}

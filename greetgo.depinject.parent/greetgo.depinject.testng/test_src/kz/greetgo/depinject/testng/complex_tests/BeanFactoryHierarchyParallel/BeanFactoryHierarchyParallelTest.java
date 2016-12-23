package kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchyParallel;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchyParallel.beans1.BeanConfigBeanFactoryHierarchyParallel1;
import kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchyParallel.beans1.BeanFactoryHierarchyParallelWindow1;
import kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchyParallel.beans2.BeanConfigBeanFactoryHierarchyParallel2;
import kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchyParallel.beans2.BeanFactoryHierarchyParallelWindow2;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig({BeanConfigBeanFactoryHierarchyParallel1.class, BeanConfigBeanFactoryHierarchyParallel2.class})
public class BeanFactoryHierarchyParallelTest extends AbstractDepinjectTestNg {

  public BeanGetter<BeanFactoryHierarchyParallelWindow1> window1;
  public BeanGetter<BeanFactoryHierarchyParallelWindow2> window2;

  public static final List<String> log = new ArrayList<>();

  @Test
  public void test() throws Exception {

    log.clear();
    log.add("--- Start");

    window1.get().lookOutWindow1();

    window2.get().lookOutWindow2();

    //log.forEach(System.out::println);

    assertThat(log.get(0)).isEqualTo("--- Start");
    assertThat(log.get(1)).isEqualTo("Look out window 1");
    assertThat(log.get(2)).isEqualTo("Look out window 2");
  }
}

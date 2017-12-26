package kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchySequence;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.depinject.testng.AbstractDepinjectTestNg;
import kz.greetgo.depinject.testng.ContainerConfig;
import kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchySequence.beans1.BeanConfigBeanFactoryHierarchySequence1;
import kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchySequence.beans1.BeanFactoryHierarchySequenceWindow1;
import kz.greetgo.depinject.testng.complex_tests.BeanFactoryHierarchySequence.beans2.BeanFactoryHierarchySequenceWindow2;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@ContainerConfig(BeanConfigBeanFactoryHierarchySequence1.class)
public class BeanFactoryHierarchySequenceTest extends AbstractDepinjectTestNg {

  public BeanGetter<BeanFactoryHierarchySequenceWindow1> window1;
  public BeanGetter<BeanFactoryHierarchySequenceWindow2> window2;

  public static final List<String> log = new ArrayList<>();

  @Test
  public void test() throws Exception {

    log.clear();
    log.add("--- Begin");

    window1.get().lookOutWindow1();

    window2.get().lookOutWindow2();

    //log.forEach(System.out::println);

    assertThat(log.get(0)).isEqualTo("--- Begin");
    assertThat(log.get(1)).isEqualTo("Look out window 1");
    assertThat(log.get(2)).isEqualTo("Look out window 2");
  }
}

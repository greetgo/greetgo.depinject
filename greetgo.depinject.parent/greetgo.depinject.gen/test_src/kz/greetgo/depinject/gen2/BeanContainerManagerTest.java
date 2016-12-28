package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.errors.ManyCandidates;
import kz.greetgo.depinject.gen.errors.NoCandidates;
import kz.greetgo.depinject.gen2.test_beans007.BeanConfig007;
import kz.greetgo.depinject.gen2.test_beans007.SomeBeanClass;
import kz.greetgo.depinject.gen2.test_beans008.BeanConfig008;
import kz.greetgo.depinject.gen2.test_beans009.Bean2;
import kz.greetgo.depinject.gen2.test_beans009.Bean3;
import kz.greetgo.depinject.gen2.test_beans009.Bean3impl;
import kz.greetgo.depinject.gen2.test_beans009.BeanConfig009;
import kz.greetgo.depinject.gen2.test_beans009.BeanPreparation009_1;
import kz.greetgo.depinject.gen2.test_beans009.BeanPreparation009_2;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class BeanContainerManagerTest {

  @Include(BeanConfig007.class)
  interface For_prepareToWrite_ManyCandidates extends BeanContainer {
    @SuppressWarnings("unused")
    SomeBeanClass asd();
  }

  @Test(expectedExceptions = ManyCandidates.class)
  public void prepareToWrite_ManyCandidates() throws Exception {

    BeanContainerManager bcm = new BeanContainerManager(For_prepareToWrite_ManyCandidates.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

  }

  class SomeLeftClass {
  }

  @Include(BeanConfig007.class)
  interface For_prepareToWrite_NoCandidates extends BeanContainer {
    @SuppressWarnings("unused")
    SomeLeftClass asd();
  }

  @Test(expectedExceptions = NoCandidates.class)
  public void prepareToWrite_NoCandidates() throws Exception {

    BeanContainerManager bcm = new BeanContainerManager(For_prepareToWrite_NoCandidates.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

  }

  @Include(BeanConfig007.class)
  interface For_prepareToWrite_NoDuplicateBeansBecauseTheseClassesNotUsed extends BeanContainer {
  }

  @Test
  public void prepareToWrite_NoDuplicateBeansBecauseTheseClassesNotUsed() throws Exception {

    BeanContainerManager bcm = new BeanContainerManager(For_prepareToWrite_NoDuplicateBeansBecauseTheseClassesNotUsed.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

  }

  @Include(BeanConfig008.class)
  interface For_prepareToWrite_preparations_1 extends BeanContainer {
  }

  @Test
  public void prepareToWrite_preparations_1() throws Exception {
    BeanContainerManager bcm = new BeanContainerManager(For_prepareToWrite_preparations_1.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    Collections.reverse(bcm.beanCreationList);
    bcm.beanCreationList.forEach(System.out::println);

    System.out.println();

    bcm.beanCreationList.get(0).beanGetterDotList.forEach(System.out::println);

  }


  @Include(BeanConfig009.class)
  interface For_prepareToWrite_preparations_2 extends BeanContainer {
  }

  @Test
  public void prepareToWrite_preparations_2() throws Exception {
    BeanContainerManager bcm = new BeanContainerManager(For_prepareToWrite_preparations_2.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    Collections.reverse(bcm.beanCreationList);
    bcm.beanCreationList.forEach(System.out::println);

    System.out.println();

    List<BeanGetterDot> beanGetterDotList = bcm.beanCreationList.get(0).beanGetterDotList;

    beanGetterDotList.forEach(System.out::println);
  }

}
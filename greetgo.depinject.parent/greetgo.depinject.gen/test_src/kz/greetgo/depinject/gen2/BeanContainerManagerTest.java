package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.errors.ManyCandidates;
import kz.greetgo.depinject.gen.errors.NoCandidates;
import kz.greetgo.depinject.gen2.test_beans007.BeanConfig007;
import kz.greetgo.depinject.gen2.test_beans007.SomeBeanClass;
import kz.greetgo.depinject.gen2.test_beans008.BeanConfig008;
import kz.greetgo.depinject.gen2.test_beans008.BeanPreparation008_1;
import kz.greetgo.depinject.gen2.test_beans008.BeanPreparation008_2;
import kz.greetgo.depinject.gen2.test_beans009.BeanConfig009;
import kz.greetgo.depinject.gen2.test_beans009.BeanPreparation009_1;
import kz.greetgo.depinject.gen2.test_beans009.BeanPreparation009_1_a;
import kz.greetgo.depinject.gen2.test_beans009.BeanPreparation009_2;
import kz.greetgo.depinject.gen2.test_beans009.BeanPreparation009_3;
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

    System.out.println("---> BEGIN Preparations:");
    bcm.preparations.forEach(System.out::println);
    System.out.println("---> END   Preparations:\n");

    Collections.reverse(bcm.beanCreationList);
    System.out.println("---> BEGIN BeanCreationList");
    bcm.beanCreationList.forEach(System.out::println);
    System.out.println("---> END   BeanCreationList");

    System.out.println();

    bcm.beanCreationList.get(0).beanGetterDotList.forEach(System.out::println);


    BeanGetterDot bean1 = bcm.beanCreationList.get(0).beanGetterDotList.get(0);

    assertThat(bean1.beanReference.getterCreations).hasSize(2);

    assertThat(bean1.beanReference.getterCreations.get(0).preparations).hasSize(1);
    assertThat(bean1.beanReference.getterCreations.get(0).preparations.get(0).beanClass.getName())
      .isEqualTo(BeanPreparation008_1.class.getName());

    assertThat(bean1.beanReference.getterCreations.get(1).preparations).hasSize(2);
    assertThat(bean1.beanReference.getterCreations.get(1).preparations.get(0).beanClass.getName())
      .isEqualTo(BeanPreparation008_2.class.getName());
    assertThat(bean1.beanReference.getterCreations.get(1).preparations.get(1).beanClass.getName())
      .isEqualTo(BeanPreparation008_1.class.getName());


    BeanGetterDot bean1impl = bcm.beanCreationList.get(0).beanGetterDotList.get(1);

    assertThat(bean1impl.beanReference.getterCreations).hasSize(1);
    assertThat(bean1impl.beanReference.getterCreations.get(0).preparations).isEmpty();


    BeanGetterDot bean2 = bcm.beanCreationList.get(0).beanGetterDotList.get(2);
    assertThat(bean2.beanReference.getterCreations).hasSize(1);

    assertThat(bean2.beanReference.getterCreations.get(0).preparations).hasSize(1);
    assertThat(bean2.beanReference.getterCreations.get(0).preparations.get(0).beanClass.getName())
      .isEqualTo(BeanPreparation008_2.class.getName());


    BeanGetterDot bean2impl = bcm.beanCreationList.get(0).beanGetterDotList.get(3);

    assertThat(bean2impl.beanReference.getterCreations).hasSize(1);
    assertThat(bean2impl.beanReference.getterCreations.get(0).preparations).isEmpty();
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

    System.out.println("---> BEGIN Preparations:");
    bcm.preparations.forEach(System.out::println);
    System.out.println("---> END   Preparations:\n");

    Collections.reverse(bcm.beanCreationList);

    System.out.println("---> BEGIN BeanCreationList");
    bcm.beanCreationList.forEach(System.out::println);
    System.out.println("---> END   BeanCreationList");

    System.out.println();

    List<BeanGetterDot> beanGetterDotList = bcm.beanCreationList.get(0).beanGetterDotList;

    beanGetterDotList.forEach(System.out::println);


    BeanGetterDot bean1 = beanGetterDotList.get(0);

    assertThat(bean1.beanReference.getterCreations).hasSize(3);

    assertThat(bean1.beanReference.getterCreations.get(0).preparations.get(0).beanClass.getName())
      .isEqualTo(BeanPreparation009_1.class.getName());
    assertThat(bean1.beanReference.getterCreations.get(0).preparations.get(1).beanClass.getName())
      .isEqualTo(BeanPreparation009_1_a.class.getName());
    assertThat(bean1.beanReference.getterCreations.get(0).preparations).hasSize(2);

    assertThat(bean1.beanReference.getterCreations.get(1).preparations.get(0).beanClass.getName())
      .isEqualTo(BeanPreparation009_2.class.getName());
    assertThat(bean1.beanReference.getterCreations.get(1).preparations.get(1).beanClass.getName())
      .isEqualTo(BeanPreparation009_1.class.getName());
    assertThat(bean1.beanReference.getterCreations.get(1).preparations.get(2).beanClass.getName())
      .isEqualTo(BeanPreparation009_1_a.class.getName());
    assertThat(bean1.beanReference.getterCreations.get(1).preparations).hasSize(3);

    assertThat(bean1.beanReference.getterCreations.get(2).preparations.get(0).beanClass.getName())
      .isEqualTo(BeanPreparation009_3.class.getName());
    assertThat(bean1.beanReference.getterCreations.get(2).preparations.get(1).beanClass.getName())
      .isEqualTo(BeanPreparation009_2.class.getName());
    assertThat(bean1.beanReference.getterCreations.get(2).preparations.get(2).beanClass.getName())
      .isEqualTo(BeanPreparation009_1.class.getName());
    assertThat(bean1.beanReference.getterCreations.get(2).preparations.get(3).beanClass.getName())
      .isEqualTo(BeanPreparation009_1_a.class.getName());
    assertThat(bean1.beanReference.getterCreations.get(2).preparations).hasSize(4);


    BeanGetterDot bean1impl = beanGetterDotList.get(1);

    assertThat(bean1impl.beanReference.getterCreations.get(0).preparations).isEmpty();
    assertThat(bean1impl.beanReference.getterCreations).hasSize(1);


    BeanGetterDot bean2 = beanGetterDotList.get(2);

    assertThat(bean2.beanReference.getterCreations).hasSize(2);

    assertThat(bean2.beanReference.getterCreations.get(0).preparations.get(0).beanClass.getName())
      .isEqualTo(BeanPreparation009_2.class.getName());
    assertThat(bean2.beanReference.getterCreations.get(0).preparations).hasSize(1);

    assertThat(bean2.beanReference.getterCreations.get(1).preparations.get(0).beanClass.getName())
      .isEqualTo(BeanPreparation009_3.class.getName());
    assertThat(bean2.beanReference.getterCreations.get(1).preparations.get(1).beanClass.getName())
      .isEqualTo(BeanPreparation009_2.class.getName());
    assertThat(bean2.beanReference.getterCreations.get(1).preparations).hasSize(2);
  }

}
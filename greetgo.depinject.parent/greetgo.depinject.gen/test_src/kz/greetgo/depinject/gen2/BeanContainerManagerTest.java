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
import kz.greetgo.depinject.gen2.test_beans011.BeanA3;
import kz.greetgo.depinject.gen2.test_beans011.BeanConfig011;
import kz.greetgo.depinject.gen2.test_beans011.ZGetters;
import kz.greetgo.depinject.gen2.test_beans015.BeanConfig015;
import kz.greetgo.depinject.gen2.test_beans015.SomeBean015;
import kz.greetgo.depinject.gen2.test_beans016.BeanConfig016;
import kz.greetgo.depinject.gen2.test_beans016.SomeBean016;
import kz.greetgo.depinject.gen2.test_beans016.SomeBeanFactory016;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

  @SuppressWarnings("unused")
  @Include(BeanConfig011.class)
  interface BeanContainer011 extends BeanContainer {
    ZGetters getZ_Getters();
  }

  @Test
  public void prepareToWrite_BeanFactories() throws Exception {
    BeanContainerManager bcm = new BeanContainerManager(BeanContainer011.class);
    //
    //
    bcm.prepareToWrite();
    //
    //

    Map<String, BeanCreation> map = bcm.usingBeanCreationList
      .stream().collect(Collectors.toMap(a -> a.beanClass.getSimpleName(), a -> a));

    System.out.println(map);

    assertThat(map.get(BeanA3.class.getSimpleName())).isInstanceOf(BeanCreationWithBeanFactory.class);

    BeanCreationWithBeanFactory bean3creation = (BeanCreationWithBeanFactory) map.get(BeanA3.class.getSimpleName());
    assertThat(bean3creation.beanFactorySource.getterCreations).hasSize(1);
  }

  @Include(BeanConfig015.class)
  interface BeanContainer_TwoBeanConfigPaths_defaultConstructor extends BeanContainer {
    SomeBean015 get();
  }

  @Test
  public void prepareToWrite_TwoBeanConfigPaths_defaultConstructor() throws Exception {

    BeanContainerManager bcm = new BeanContainerManager(BeanContainer_TwoBeanConfigPaths_defaultConstructor.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    assertThat(bcm.usingBeanCreationList).hasSize(1);

  }

  @Include(BeanConfig016.class)
  interface BeanContainer_TwoBeanConfigPaths_factoryMethod extends BeanContainer {
    List<SomeBean016> get();
  }

  @Test
  public void prepareToWrite_TwoBeanConfigPaths_factoryMethod() throws Exception {

    BeanContainerManager bcm = new BeanContainerManager(BeanContainer_TwoBeanConfigPaths_factoryMethod.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    int factoryCount = 0, someBean016_count = 0;

    for (BeanCreation bc : bcm.usingBeanCreationList) {
      if (SomeBean016.class.isAssignableFrom(bc.beanClass)) someBean016_count++;
      if (SomeBeanFactory016.class.isAssignableFrom(bc.beanClass)) factoryCount++;
    }

    assertThat(factoryCount).isEqualTo(1);
    assertThat(someBean016_count).describedAs("If SomeBean016 is 6 " +
      "then error in method BeanCreationWithFactoryMethod.equals()").isEqualTo(3);
  }
}
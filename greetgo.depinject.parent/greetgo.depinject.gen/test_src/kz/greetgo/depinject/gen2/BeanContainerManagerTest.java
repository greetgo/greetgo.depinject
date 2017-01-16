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
import kz.greetgo.depinject.gen2.test_beans017.beans.core.UsingBeanFactoryRoom;
import kz.greetgo.depinject.gen2.test_beans017.util.BeanConfigUsingBigBeanFactory;
import kz.greetgo.depinject.gen2.test_beans017.util.BeanConfigUsingSmallBeanFactory;
import kz.greetgo.depinject.gen2.test_beans019.Bean019;
import kz.greetgo.depinject.gen2.test_beans019.BeanConfig019;
import kz.greetgo.depinject.gen2.test_beans021.Bean021;
import kz.greetgo.depinject.gen2.test_beans021.Bean021_free;
import kz.greetgo.depinject.gen2.test_beans021.BeanConfig021;
import kz.greetgo.depinject.gen2.test_beans020.Bean020;
import kz.greetgo.depinject.gen2.test_beans020.Bean020_free;
import kz.greetgo.depinject.gen2.test_beans020.BeanConfig020;
import org.fest.assertions.api.Assertions;
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

  @Include(BeanConfigUsingSmallBeanFactory.class)
  interface BeanContainer_BeanConfigUsingSmallBeanFactory extends BeanContainer {
    UsingBeanFactoryRoom get();
  }

  @Include(BeanConfigUsingBigBeanFactory.class)
  interface BeanContainer_BeanConfigUsingBigBeanFactory extends BeanContainer {
    UsingBeanFactoryRoom get();
  }

  @Test
  public void prepareToWrite_BeanConfigUsingSmallBeanFactory() throws Exception {
    BeanContainerManager bcm = new BeanContainerManager(BeanContainer_BeanConfigUsingSmallBeanFactory.class);
    bcm.prepareToWrite();
  }

  @Test
  public void prepareToWrite_BeanConfigUsingBigBeanFactory() throws Exception {
    BeanContainerManager bcm = new BeanContainerManager(BeanContainer_BeanConfigUsingBigBeanFactory.class);
    bcm.prepareToWrite();
  }

  @Include(BeanConfig019.class)
  interface BeanContainer_019 extends BeanContainer {
    Bean019 get();
  }

  @Test
  public void prepareToWrite_019() throws Exception {
    BeanContainerManager bcm = new BeanContainerManager(BeanContainer_019.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    assertThat(bcm.beanContainerMethodList).describedAs("beanContainerMethodList.size must be 1").hasSize(1);
    assertThat(bcm.allBeanReferences).describedAs("allBeanReferences.size must be 1").hasSize(1);
    assertThat(bcm.writingBeanReferences).describedAs("writingBeanReferences.size must be absent").isEmpty();
  }

  @SuppressWarnings("unused")
  @Include(BeanConfig020.class)
  interface BeanContainer020 extends BeanContainer {
    Bean020 bean020();

    Bean020_free bean020_free();
  }

  @Test
  public void prepareToWrite_replacer_iface() throws Exception {
    BeanContainerManager bcm = new BeanContainerManager(BeanContainer020.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    assertThat(bcm.replacers).hasSize(1);

    assertThat(bcm.writingGetterCreations).hasSize(1);
    assertThat(bcm.writingGetterCreations.get(0).replacers).hasSize(1);
    assertThat(bcm.writingGetterCreations.get(0).beanCreation.beanClass.getName()).isEqualTo(Bean020.class.getName());
    assertThat(bcm.beanCreationList).hasSize(3);
    assertThat(bcm.usingBeanCreationList).hasSize(3);
  }

  @Test
  public void prepareToWrite_replacerForItself() throws Exception {
    Assertions.fail("Check that replacer cannot be replacer for itself");
  }

  @SuppressWarnings("unused")
  @Include(BeanConfig021.class)
  interface BeanContainer021 extends BeanContainer {
    Bean021 bean021();

    Bean021_free bean021_free();
  }

  @Test
  public void prepareToWrite_replacer_ann() throws Exception {
    BeanContainerManager bcm = new BeanContainerManager(BeanContainer021.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    assertThat(bcm.replacers).hasSize(1);

    assertThat(bcm.writingGetterCreations).hasSize(1);
    assertThat(bcm.writingGetterCreations.get(0).replacers).hasSize(1);
    assertThat(bcm.writingGetterCreations.get(0).beanCreation.beanClass.getName()).isEqualTo(Bean021.class.getName());
    assertThat(bcm.beanCreationList).hasSize(3);
    assertThat(bcm.usingBeanCreationList).hasSize(3);
  }
}
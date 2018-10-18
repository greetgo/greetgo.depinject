package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.errors.ManyCandidates;
import kz.greetgo.depinject.gen.errors.NoCandidates;
import kz.greetgo.depinject.gen.errors.NoDefaultBeanFactory;
import kz.greetgo.depinject.gen.errors.NotPublicBeanWithoutConstructor;
import kz.greetgo.depinject.gen.test_beans007.BeanConfig007;
import kz.greetgo.depinject.gen.test_beans007.SomeBeanClass;
import kz.greetgo.depinject.gen.test_beans008.BeanConfig008;
import kz.greetgo.depinject.gen.test_beans008.BeanPreparation008_1;
import kz.greetgo.depinject.gen.test_beans008.BeanPreparation008_2;
import kz.greetgo.depinject.gen.test_beans009.BeanConfig009;
import kz.greetgo.depinject.gen.test_beans009.BeanPreparation009_1;
import kz.greetgo.depinject.gen.test_beans009.BeanPreparation009_1_a;
import kz.greetgo.depinject.gen.test_beans009.BeanPreparation009_2;
import kz.greetgo.depinject.gen.test_beans009.BeanPreparation009_3;
import kz.greetgo.depinject.gen.test_beans011.BeanA3;
import kz.greetgo.depinject.gen.test_beans011.BeanConfig011;
import kz.greetgo.depinject.gen.test_beans011.ZGetters;
import kz.greetgo.depinject.gen.test_beans015.BeanConfig015;
import kz.greetgo.depinject.gen.test_beans015.SomeBean015;
import kz.greetgo.depinject.gen.test_beans016.BeanConfig016;
import kz.greetgo.depinject.gen.test_beans016.SomeBean016;
import kz.greetgo.depinject.gen.test_beans016.SomeBeanFactory016;
import kz.greetgo.depinject.gen.test_beans017.beans.core.UsingBeanFactoryRoom;
import kz.greetgo.depinject.gen.test_beans017.util.BeanConfigUsingBigBeanFactory;
import kz.greetgo.depinject.gen.test_beans017.util.BeanConfigUsingSmallBeanFactory;
import kz.greetgo.depinject.gen.test_beans019.Bean019;
import kz.greetgo.depinject.gen.test_beans019.BeanConfig019;
import kz.greetgo.depinject.gen.test_beans020.Bean020;
import kz.greetgo.depinject.gen.test_beans020.Bean020_free;
import kz.greetgo.depinject.gen.test_beans020.BeanConfig020;
import kz.greetgo.depinject.gen.test_beans021.Bean021;
import kz.greetgo.depinject.gen.test_beans021.Bean021_free;
import kz.greetgo.depinject.gen.test_beans021.BeanConfig021;
import kz.greetgo.depinject.gen.test_beans022.Bean022;
import kz.greetgo.depinject.gen.test_beans022.BeanConfig022;
import kz.greetgo.depinject.gen.test_beans022.Iface022;
import kz.greetgo.depinject.gen.test_beans023.Bean023;
import kz.greetgo.depinject.gen.test_beans023.BeanConfig023;
import kz.greetgo.depinject.gen.test_beans023.Iface023;
import kz.greetgo.depinject.gen.test_beans024.Bean024;
import kz.greetgo.depinject.gen.test_beans024.BeanConfig024;
import kz.greetgo.depinject.gen.test_beans024.Iface024;
import kz.greetgo.depinject.gen.test_beans025.Bean025;
import kz.greetgo.depinject.gen.test_beans025.BeanConfig025;
import kz.greetgo.depinject.gen.test_beans025.BeanWithRefToReplacer;
import kz.greetgo.depinject.gen.test_beans026.Bean026;
import kz.greetgo.depinject.gen.test_beans026.BeanConfig026;
import kz.greetgo.depinject.gen.test_beans026.Replacer026_A;
import kz.greetgo.depinject.gen.test_beans026.Replacer026_B;
import kz.greetgo.depinject.gen.test_beans029.BeanConfig029;
import kz.greetgo.depinject.gen.test_beans029.beans4.Bean029_4;
import kz.greetgo.depinject.gen.test_beans030.BeanConfig030;
import kz.greetgo.depinject.gen.test_beans030.beans4.Bean030_4;
import kz.greetgo.depinject.gen.test_beans031.BeanConfig031;
import kz.greetgo.depinject.gen.test_beans031.beans4.Bean031_4;
import kz.greetgo.depinject.gen.test_beans032.p01_in_the_bean.Bean032_01;
import kz.greetgo.depinject.gen.test_beans032.p01_in_the_bean.BeanConfig032_01;
import kz.greetgo.depinject.gen.test_beans032.p02_in_parent_class.Bean032_02;
import kz.greetgo.depinject.gen.test_beans032.p02_in_parent_class.BeanConfig032_02;
import kz.greetgo.depinject.gen.test_beans032.p02_in_parent_class.ParentBean032_02;
import kz.greetgo.depinject.gen.test_beans032.p03_in_parent_class_markedClass.Bean032_03;
import kz.greetgo.depinject.gen.test_beans032.p03_in_parent_class_markedClass.BeanConfig032_03;
import kz.greetgo.depinject.gen.test_beans032.p03_in_parent_class_markedClass.ParentBean032_03;
import kz.greetgo.depinject.gen.test_beans032.p04_in_the_bean_OK.Bean032_04;
import kz.greetgo.depinject.gen.test_beans032.p04_in_the_bean_OK.BeanConfig032_04;
import kz.greetgo.depinject.gen.test_beans032.p05_in_parent_class_OK.Bean032_05;
import kz.greetgo.depinject.gen.test_beans032.p05_in_parent_class_OK.BeanConfig032_05;
import kz.greetgo.depinject.gen.test_beans032.p06_in_parent_class_markedParentClass_OK.Bean032_06;
import kz.greetgo.depinject.gen.test_beans032.p06_in_parent_class_markedParentClass_OK.BeanConfig032_06;
import kz.greetgo.depinject.gen.test_beans032.p07_in_the_bean_markedClass_OK.Bean032_07;
import kz.greetgo.depinject.gen.test_beans032.p07_in_the_bean_markedClass_OK.BeanConfig032_07;
import kz.greetgo.depinject.gen.test_beans032.p08_in_the_bean_ignoreUnused.Bean032_08;
import kz.greetgo.depinject.gen.test_beans032.p08_in_the_bean_ignoreUnused.BeanConfig032_08;
import kz.greetgo.depinject.gen.test_beans032.p09_private_bean_getters.Bean032_09_main;
import kz.greetgo.depinject.gen.test_beans032.p09_private_bean_getters.BeanConfig032_09;
import kz.greetgo.depinject.gen.test_beans032.p10_prepare_bg_without_constructor.Bean032_10_main;
import kz.greetgo.depinject.gen.test_beans032.p10_prepare_bg_without_constructor.BeanConfig032_10;
import kz.greetgo.depinject.gen.test_beans032.p11_skipping_inject_constructor_args.Bean032_11_main;
import kz.greetgo.depinject.gen.test_beans032.p11_skipping_inject_constructor_args.BeanConfig032_11;
import org.fest.assertions.api.Assertions;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
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
  public void prepareToWrite_ManyCandidates() {

    Context context = new Context();
    BeanContainerManager bcm = context.createManager(For_prepareToWrite_ManyCandidates.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

  }

  class SomeLeftClass {}

  @Include(BeanConfig007.class)
  interface For_prepareToWrite_NoCandidates extends BeanContainer {
    @SuppressWarnings("unused")
    SomeLeftClass asd();
  }

  @Test(expectedExceptions = NoCandidates.class)
  public void prepareToWrite_NoCandidates() {

    Context context = new Context();
    BeanContainerManager bcm = context.createManager(For_prepareToWrite_NoCandidates.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

  }

  @Include(BeanConfig007.class)
  interface For_prepareToWrite_NoDuplicateBeansBecauseTheseClassesNotUsed extends BeanContainer {}

  @Test
  public void prepareToWrite_NoDuplicateBeansBecauseTheseClassesNotUsed() {

    Context context = new Context();
    BeanContainerManager bcm = context.createManager(For_prepareToWrite_NoDuplicateBeansBecauseTheseClassesNotUsed.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

  }

  @Include(BeanConfig008.class)
  interface For_prepareToWrite_preparations_1 extends BeanContainer {}

  @Test
  public void prepareToWrite_preparations_1() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(For_prepareToWrite_preparations_1.class);

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

    bcm.beanCreationList.get(0).beanGetterInPublicFieldList.forEach(System.out::println);


    BeanGetterInPublicField bean1 = bcm.beanCreationList.get(0).beanGetterInPublicFieldList.get(0);

    assertThat(bean1.beanReference.getterCreations).hasSize(2);

    assertThat(bean1.beanReference.getterCreations.get(0).preparations).hasSize(1);
    assertThat(bean1.beanReference.getterCreations.get(0).preparations.get(0).beanClass.getName())
        .isEqualTo(BeanPreparation008_1.class.getName());

    assertThat(bean1.beanReference.getterCreations.get(1).preparations).hasSize(2);
    assertThat(bean1.beanReference.getterCreations.get(1).preparations.get(0).beanClass.getName())
        .isEqualTo(BeanPreparation008_2.class.getName());
    assertThat(bean1.beanReference.getterCreations.get(1).preparations.get(1).beanClass.getName())
        .isEqualTo(BeanPreparation008_1.class.getName());


    BeanGetterInPublicField bean1impl = bcm.beanCreationList.get(0).beanGetterInPublicFieldList.get(1);

    assertThat(bean1impl.beanReference.getterCreations).hasSize(1);
    assertThat(bean1impl.beanReference.getterCreations.get(0).preparations).isEmpty();


    BeanGetterInPublicField bean2 = bcm.beanCreationList.get(0).beanGetterInPublicFieldList.get(2);
    assertThat(bean2.beanReference.getterCreations).hasSize(1);

    assertThat(bean2.beanReference.getterCreations.get(0).preparations).hasSize(1);
    assertThat(bean2.beanReference.getterCreations.get(0).preparations.get(0).beanClass.getName())
        .isEqualTo(BeanPreparation008_2.class.getName());


    BeanGetterInPublicField bean2impl = bcm.beanCreationList.get(0).beanGetterInPublicFieldList.get(3);

    assertThat(bean2impl.beanReference.getterCreations).hasSize(1);
    assertThat(bean2impl.beanReference.getterCreations.get(0).preparations).isEmpty();
  }


  @Include(BeanConfig009.class)
  interface For_prepareToWrite_preparations_2 extends BeanContainer {}

  @Test
  public void prepareToWrite_preparations_2() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(For_prepareToWrite_preparations_2.class);

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

    List<BeanGetterInPublicField> beanGetterInPublicFieldList = bcm.beanCreationList.get(0).beanGetterInPublicFieldList;

    beanGetterInPublicFieldList.forEach(System.out::println);


    BeanGetterInPublicField bean1 = beanGetterInPublicFieldList.get(0);

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


    BeanGetterInPublicField bean1impl = beanGetterInPublicFieldList.get(1);

    assertThat(bean1impl.beanReference.getterCreations.get(0).preparations).isEmpty();
    assertThat(bean1impl.beanReference.getterCreations).hasSize(1);


    BeanGetterInPublicField bean2 = beanGetterInPublicFieldList.get(2);

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
  public void prepareToWrite_BeanFactories() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer011.class);
    //
    //
    bcm.prepareToWrite();
    //
    //

    Map<String, BeanCreation> map = bcm.usingBeanCreationList
        .stream().collect(Collectors.toMap(a -> a.beanClass.getSimpleName(), a -> a));

    assertThat(map.get(BeanA3.class.getSimpleName())).isInstanceOf(BeanCreationWithBeanFactory.class);

    BeanCreationWithBeanFactory bean3creation = (BeanCreationWithBeanFactory) map.get(BeanA3.class.getSimpleName());
    assertThat(bean3creation.beanFactorySource.getterCreations).hasSize(1);
  }

  @Include(BeanConfig015.class)
  interface BeanContainer_TwoBeanConfigPaths_defaultConstructor extends BeanContainer {
    SomeBean015 get();
  }

  @Test
  public void prepareToWrite_TwoBeanConfigPaths_defaultConstructor() {

    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer_TwoBeanConfigPaths_defaultConstructor.class);

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
  public void prepareToWrite_TwoBeanConfigPaths_factoryMethod() {

    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer_TwoBeanConfigPaths_factoryMethod.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    int factoryCount = 0, someBean016_count = 0;

    for (BeanCreation bc : bcm.usingBeanCreationList) {
      if (SomeBean016.class.isAssignableFrom(bc.beanClass)) { someBean016_count++; }
      if (SomeBeanFactory016.class.isAssignableFrom(bc.beanClass)) { factoryCount++; }
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
  public void prepareToWrite_BeanConfigUsingSmallBeanFactory() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer_BeanConfigUsingSmallBeanFactory.class);
    bcm.prepareToWrite();
  }

  @Test
  public void prepareToWrite_BeanConfigUsingBigBeanFactory() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer_BeanConfigUsingBigBeanFactory.class);
    bcm.prepareToWrite();
  }

  @Include(BeanConfig019.class)
  interface BeanContainer_019 extends BeanContainer {
    Bean019 get();
  }

  @Test
  public void prepareToWrite_019() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer_019.class);

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
  public void prepareToWrite_replacer_interface() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer020.class);

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


  @SuppressWarnings("unused")
  @Include(BeanConfig021.class)
  interface BeanContainer021 extends BeanContainer {
    Bean021 bean021();

    Bean021_free bean021_free();
  }

  @Test
  public void prepareToWrite_replacer_ann() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer021.class);

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

  @SuppressWarnings("unused")
  @Include(BeanConfig022.class)
  interface BeanContainer022 extends BeanContainer {
    Iface022 iface022();

    Bean022 bean022();

    Bean022 bean022_more();

    Iface022 interface022_more();
  }

  @Test
  public void prepareToWrite_replacer_returnClass() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer022.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    bcm.beanContainerMethodList.forEach(System.out::println);

    assertThat(bcm.beanContainerMethodList).hasSize(4);
    assertThat(bcm.replacers).hasSize(1);
    assertThat(bcm.usingBeanCreationList).hasSize(2);
    assertThat(bcm.writingGetterCreations).hasSize(2);

    assertThat(bcm.writingGetterCreations.get(0).replacers).hasSize(1);
    assertThat(bcm.writingGetterCreations.get(1).replacers).hasSize(1);

  }

  @SuppressWarnings("unused")
  @Include(BeanConfig023.class)
  interface BeanContainer023 extends BeanContainer {
    Iface023 iface023();

    Bean023 bean023();

    Bean023 bean023_more();

    Iface023 interface023_more();
  }

  @Test
  public void prepareToWrite_distinctGetterCreation() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer023.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    assertThat(bcm.beanContainerMethodList).hasSize(4);
    assertThat(bcm.replacers).isEmpty();
    assertThat(bcm.usingBeanCreationList).hasSize(2);
    assertThat(bcm.writingGetterCreations).hasSize(1);
    assertThat(bcm.writingBeanReferences).isEmpty();
  }

  @SuppressWarnings("unused")
  @Include(BeanConfig024.class)
  interface BeanContainer024 extends BeanContainer {
    Iface024 iface024();

    Bean024 bean024();

    Bean024 bean024_more();

    Iface024 interface024_more();
  }

  @Test
  public void prepareToWrite_noGetterCreation() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer024.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    assertThat(bcm.beanContainerMethodList).hasSize(4);
    assertThat(bcm.replacers).isEmpty();
    assertThat(bcm.usingBeanCreationList).hasSize(1);
    assertThat(bcm.writingGetterCreations).isEmpty();
    assertThat(bcm.writingBeanReferences).isEmpty();
  }

  @SuppressWarnings("unused")
  @Include(BeanConfig025.class)
  interface BeanContainer025 extends BeanContainer {
    Bean025 bean025();

    BeanWithRefToReplacer bean_with_ref_to_replacer();
  }

  @Test
  public void prepareToWrite_replacerForItself() {

    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer025.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    assertThat(bcm.replacers).hasSize(2);

//    bcm.allBeanReferences.forEach(a -> System.out.println(a.toFullString()));

    Map<String, BeanReference> map = new HashMap<>();
    bcm.allBeanReferences.forEach(r -> map.put(r.sourceClass.getSimpleName(), r));
//    map.keySet().forEach(System.out::println);

    BeanReference beanRef = map.get("Bean025");
    assertThat(beanRef.getterCreations).hasSize(1);
    assertThat(beanRef.getterCreations.get(0).replacers).hasSize(2);

    BeanReference replaceBR = map.get("Replacer025");
    assertThat(replaceBR.getterCreations).hasSize(1);
    assertThat(replaceBR.getterCreations.get(0).replacers).describedAs("replacer cannot be replaced").isEmpty();

    BeanReference replaceBR_more = map.get("Replacer025_more");
    assertThat(replaceBR_more.getterCreations).hasSize(1);
    assertThat(replaceBR_more.getterCreations.get(0).replacers).describedAs("replacer cannot be replaced").isEmpty();
  }


  @SuppressWarnings("unused")
  @Include(BeanConfig026.class)
  interface BeanContainer026 extends BeanContainer {
    Bean026 bean026();
  }

  @Test
  public void prepareToWrite_replacePriority() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer026.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    assertThat(bcm.replacers).hasSize(2);
    assertThat(bcm.replacers.get(0).beanClass.getName()).isEqualTo(Replacer026_B.class.getName());
    assertThat(bcm.replacers.get(1).beanClass.getName()).isEqualTo(Replacer026_A.class.getName());
  }

  @Include(BeanConfig029.class)
  interface BeanContainer029 extends BeanContainer {
    @SuppressWarnings("unused")
    Bean029_4 bean();
  }

  @Test
  public void prepareToWrite_NoCandidates2() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer029.class);

    NoCandidates error = null;

    try {
      //
      //
      bcm.prepareToWrite();
      //
      //
    } catch (NoCandidates e) {
      error = e;
    }

    assertThat(error).isNotNull();
    assert error != null;
    System.out.println(error.getMessage());

    List<BeanConfigTree.TreeElement> list = error.configTree.tree.stream()
        .filter(a -> a.type != BeanConfigTree.TreeElementType.Bean)
        .collect(Collectors.toList());

    assertThat(list.get(0).type).isEqualTo(BeanConfigTree.TreeElementType.ROOT);
    assertThat(list.get(0).message).isEqualTo(BeanContainer029.class.getName());

    assertThat(list.get(4).type).isEqualTo(BeanConfigTree.TreeElementType.ScanPackage);
    assertThat(list.get(4).message).isEqualTo(Bean029_4.class.getPackage().getName());

  }

  @Include(BeanConfig030.class)
  interface BeanContainer030 extends BeanContainer {
    @SuppressWarnings("unused")
    Bean030_4 bean();
  }

  @Test
  public void prepareToWrite_ManyCandidates2() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer030.class);

    ManyCandidates error = null;

    try {
      //
      //
      bcm.prepareToWrite();
      //
      //
    } catch (ManyCandidates e) {
      error = e;
    }

    assertThat(error).isNotNull();
    assert error != null;
    System.out.println(error.getMessage());

    List<BeanConfigTree.TreeElement> list = error.configTree.tree.stream()
        .filter(a -> a.type != BeanConfigTree.TreeElementType.Bean)
        .collect(Collectors.toList());

    assertThat(list.get(0).type).isEqualTo(BeanConfigTree.TreeElementType.ROOT);
    assertThat(list.get(0).message).isEqualTo(BeanContainer030.class.getName());

    assertThat(list.get(4).type).isEqualTo(BeanConfigTree.TreeElementType.ScanPackage);
    assertThat(list.get(4).message).isEqualTo(Bean030_4.class.getPackage().getName());
  }

  @Include(BeanConfig031.class)
  interface BeanContainer031 extends BeanContainer {
    @SuppressWarnings("unused")
    Bean031_4 bean();
  }

  @Test
  public void prepareToWrite_NoDefaultBeanFactory() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer031.class);

    NoDefaultBeanFactory error = null;

    try {
      //
      //
      bcm.prepareToWrite();
      //
      //
    } catch (NoDefaultBeanFactory e) {
      error = e;
    }

    assertThat(error).isNotNull();
    assert error != null;
    System.out.println(error.getMessage());

    List<BeanConfigTree.TreeElement> list = error.configTree.tree.stream()
        .filter(a -> a.type != BeanConfigTree.TreeElementType.Bean)
        .collect(Collectors.toList());

    assertThat(list.get(0).type).isEqualTo(BeanConfigTree.TreeElementType.ROOT);
    assertThat(list.get(0).message).isEqualTo(BeanContainer031.class.getName());

    assertThat(list.get(4).type).isEqualTo(BeanConfigTree.TreeElementType.ScanPackage);
    assertThat(list.get(4).message).isEqualTo(Bean031_4.class.getPackage().getName());
  }

  @Include(BeanConfig032_01.class)
  interface BeanContainer032_01 extends BeanContainer {
    @SuppressWarnings("unused")
    Bean032_01 bean();
  }

  @Test
  public void prepareToWrite_notPublicBeanGetter_p01_in_the_bean() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer032_01.class);

    NotPublicBeanWithoutConstructor error = null;

    try {
      //
      //
      bcm.prepareToWrite();
      //
      //

      Assertions.fail("Must be exception " + NotPublicBeanWithoutConstructor.class.getSimpleName());
    } catch (NotPublicBeanWithoutConstructor e) {
      error = e;
    }

    assertThat(error.containsFieldClass.getName()).isEqualTo(Bean032_01.class.getName());
    assertThat(error.beanGetterField.getName()).isEqualTo("notPublicBeanGetterField_5432656255");
  }

  @Include(BeanConfig032_02.class)
  interface BeanContainer032_02 extends BeanContainer {
    @SuppressWarnings("unused")
    Bean032_02 bean();
  }

  @Test
  public void prepareToWrite_notPublicBeanGetter_p02_in_parent_class() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer032_02.class);

    NotPublicBeanWithoutConstructor error = null;

    try {
      //
      //
      bcm.prepareToWrite();
      //
      //

      Assertions.fail("Must be exception " + NotPublicBeanWithoutConstructor.class.getSimpleName());
    } catch (NotPublicBeanWithoutConstructor e) {
      error = e;
    }

    assertThat(error.containsFieldClass.getName()).isEqualTo(ParentBean032_02.class.getName());
    assertThat(error.beanGetterField.getName()).isEqualTo("notPublicBeanGetterField_897856654");
  }

  @Include(BeanConfig032_03.class)
  interface BeanContainer032_03 extends BeanContainer {
    @SuppressWarnings("unused")
    Bean032_03 bean();
  }

  @Test
  public void prepareToWrite_notPublicBeanGetter_p03_in_parent_class_markedClass() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer032_03.class);

    NotPublicBeanWithoutConstructor error = null;

    try {
      //
      //
      bcm.prepareToWrite();
      //
      //

      Assertions.fail("Must be exception " + NotPublicBeanWithoutConstructor.class.getSimpleName());
    } catch (NotPublicBeanWithoutConstructor e) {
      error = e;
    }

    assertThat(error.containsFieldClass.getName()).isEqualTo(ParentBean032_03.class.getName());
    assertThat(error.beanGetterField.getName()).isEqualTo("notPublicBeanGetterField_26374892");
  }

  @Include(BeanConfig032_04.class)
  interface BeanContainer032_04 extends BeanContainer {
    @SuppressWarnings("unused")
    Bean032_04 bean();
  }

  @Test
  public void prepareToWrite_notPublicBeanGetter_p04_in_the_bean_OK() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer032_04.class);

    //
    //
    bcm.prepareToWrite();
    //
    //
  }

  @Include(BeanConfig032_05.class)
  interface BeanContainer032_05 extends BeanContainer {
    @SuppressWarnings("unused")
    Bean032_05 bean();
  }

  @Test
  public void prepareToWrite_notPublicBeanGetter_p05_in_parent_class_OK() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer032_05.class);

    //
    //
    bcm.prepareToWrite();
    //
    //
  }

  @Include(BeanConfig032_06.class)
  interface BeanContainer032_06 extends BeanContainer {
    @SuppressWarnings("unused")
    Bean032_06 bean();
  }

  @Test
  public void prepareToWrite_notPublicBeanGetter_p06_in_parent_class_markedParentClass_OK() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer032_06.class);

    //
    //
    bcm.prepareToWrite();
    //
    //
  }

  @Include(BeanConfig032_07.class)
  interface BeanContainer032_07 extends BeanContainer {
    @SuppressWarnings("unused")
    Bean032_07 bean();
  }

  @Test
  public void prepareToWrite_notPublicBeanGetter_p07_in_the_bean_markedClass_OK() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer032_07.class);

    //
    //
    bcm.prepareToWrite();
    //
    //
  }

  @Include(BeanConfig032_08.class)
  interface BeanContainer032_08 extends BeanContainer {
    @SuppressWarnings("unused")
    Bean032_08 bean();
  }

  @Test
  public void prepareToWrite_notPublicBeanGetter_p08_in_the_bean_ignoreUnused() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer032_08.class);

    //
    //
    bcm.prepareToWrite();
    //
    //
  }

  @Include(BeanConfig032_09.class)
  interface BeanContainer032_09 extends BeanContainer {
    @SuppressWarnings("unused")
    Bean032_09_main bean();
  }

  @Test
  public void prepareToWrite_privateBeanGettersUsedInConstructorArgs() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer032_09.class);

    //
    //
    bcm.prepareToWrite();
    //
    //
  }

  @Include(BeanConfig032_10.class)
  interface BeanContainer032_10 extends BeanContainer {
    @SuppressWarnings("unused")
    Bean032_10_main bean();
  }

  @Test
  public void prepareToWrite_privateBeanGetter_NOT_UsedInConstructorArgs() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer032_10.class);

    NotPublicBeanWithoutConstructor error = null;

    try {
      //
      //
      bcm.prepareToWrite();
      //
      //
    } catch (NotPublicBeanWithoutConstructor e) {
      error = e;
    }

    assertThat(error).isNotNull();
    assert error != null;
    assertThat(error.beanGetterField.getName()).isEqualTo("bean032_09_3");
    assertThat(error.containsFieldClass.getName()).isEqualTo(Bean032_10_main.class.getName());
    assertThat(error.beanClass).isNull();
  }

  @Include(BeanConfig032_11.class)
  interface BeanContainer032_11 extends BeanContainer {
    @SuppressWarnings("unused")
    Bean032_11_main bean();
  }

  @Test
  public void prepareToWrite_skipping_inject_constructor_args() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer032_11.class);

    //
    //
    bcm.prepareToWrite();
    //
    //
  }
}

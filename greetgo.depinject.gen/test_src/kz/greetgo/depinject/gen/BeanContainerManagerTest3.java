package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.core.Qualifier;
import kz.greetgo.depinject.gen.errors.QualifierNotMatched;
import kz.greetgo.depinject.gen.t03x.test_beans034.BeanConfig034;
import kz.greetgo.depinject.gen.t03x.test_beans034.MainBean034;
import kz.greetgo.depinject.gen.t03x.test_beans034.TargetBean034_1;
import kz.greetgo.depinject.gen.t03x.test_beans035.BeanConfig035;
import kz.greetgo.depinject.gen.t03x.test_beans035.MainBean035;
import kz.greetgo.depinject.gen.t03x.test_beans037.BeanConfig037;
import kz.greetgo.depinject.gen.t03x.test_beans037.BeanTarget037;
import kz.greetgo.depinject.gen.t03x.test_beans037.MainBean037;
import kz.greetgo.depinject.gen.t03x.test_beans038.Bean038;
import kz.greetgo.depinject.gen.t03x.test_beans038.Bean038_1;
import kz.greetgo.depinject.gen.t03x.test_beans038.Bean038_2;
import kz.greetgo.depinject.gen.t03x.test_beans038.Bean038_3;
import kz.greetgo.depinject.gen.t03x.test_beans038.BeanConfig038;
import kz.greetgo.depinject.gen.t03x.test_beans038.BeanRef038_privateBeanGetter_strExactly;
import org.fest.assertions.api.Assertions;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.fest.assertions.api.Assertions.assertThat;

public class BeanContainerManagerTest3 {

  @Include(BeanConfig034.class)
  interface BeanContainer034 extends BeanContainer {
    @SuppressWarnings("unused")
    MainBean034 mainBean();
  }

  @Test
  public void using_qualifier() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer034.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    BeanCreation mainBeanCreation = bcm.beanCreationList.stream()
        .filter(a -> a.beanClass == MainBean034.class)
        .findAny()
        .orElseThrow(() -> new RuntimeException("No bean of " + MainBean034.class));

    BeanGetterInPublicField beanGetter = mainBeanCreation.beanGetterInPublicFieldList.get(0);
    Class<?> actualTargetClass = beanGetter.beanReference.getterCreations.get(0).beanCreation.beanClass;

    assertThat(actualTargetClass.getName()).isEqualTo(TargetBean034_1.class.getName());
  }

  @Include(BeanConfig035.class)
  interface BeanContainer035 extends BeanContainer {
    @SuppressWarnings("unused")
    MainBean035 mainBean();
  }

  @Test(expectedExceptions = QualifierNotMatched.class)
  public void not_found_bean_by_qualifier() {

    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer035.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    Assertions.fail("Need throws QualifierNotFound");
  }

  @Include(BeanConfig037.class)
  interface BeanContainer037 extends BeanContainer {
    @SuppressWarnings("unused")
    MainBean037 mainBean();
  }

  @Test
  public void using_qualifier_for_method_factored_beans() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer037.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

//    bcm.usingBeanReferences.forEach(ref -> {
//      System.out.println("sc = " + ref.sourceClass);
//      System.out.println("place = " + ref.place.display());
//      System.out.println();
//    });

    BeanReference target = bcm.usingBeanReferences.stream()
        .filter(ref -> ref.sourceClass == BeanTarget037.class)
        .findAny()
        .orElseThrow(TestUtil.ElementNotFound::new);

    assertThat(target.getterCreations).hasSize(1);

    assertThat(target.getterCreations.get(0).beanCreation.beanId()).isEqualTo("bean2");
  }

  @Include(BeanConfig038.class)
  interface BeanContainer038_1 extends BeanContainer {
    @SuppressWarnings("unused")
    @Qualifier("bean_hello_.*")
    List<Bean038> qualifier_str_exactly();
  }

  @Test
  public void qualifier_str_exactly() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer038_1.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

//    bcm.usingBeanReferences.forEach(ref -> {
//      System.out.println("sc = " + ref.sourceClass);
//      System.out.println("place = " + ref.place.display());
//      System.out.println();
//    });

    BeanReference target = bcm.usingBeanReferences.stream()
        .filter(ref -> ref.sourceClass == Bean038.class)
        .findAny()
        .orElseThrow(TestUtil.ElementNotFound::new);

    assertThat(target.getterCreations).hasSize(1);

    assertThat(target.getterCreations.get(0).beanCreation.beanId()).isEqualTo("bean_hello_.*");
  }

  @Include(BeanConfig038.class)
  interface BeanContainer038_2 extends BeanContainer {
    @SuppressWarnings("unused")
    @Qualifier(value = "bean_hello_.*", regexp = true)
    List<Bean038> qualifier_regexp();
  }

  @Test
  public void qualifier_regexp() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer038_2.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

//    bcm.usingBeanReferences.forEach(ref -> {
//      System.out.println("sc = " + ref.sourceClass);
//      System.out.println("place = " + ref.place.display());
//      System.out.println();
//    });

    BeanReference target = bcm.usingBeanReferences.stream()
        .filter(ref -> ref.sourceClass == Bean038.class)
        .findAny()
        .orElseThrow(TestUtil.ElementNotFound::new);

    assertThat(target.getterCreations).hasSize(3);

    Map<String, List<GetterCreation>> grouped = target.getterCreations
        .stream()
        .collect(Collectors.groupingBy(a -> a.beanCreation.beanId()));

    assertThat(grouped.get("bean_hello_.*")).hasSize(1);
    assertThat(grouped.get("bean_hello_2")).hasSize(1);
    assertThat(grouped.get("bean_hello_3")).hasSize(1);

    assertThat(grouped.get("bean_hello_.*").get(0).beanCreation.beanClass.getName()).isEqualTo(Bean038_1.class.getName());
    assertThat(grouped.get("bean_hello_2").get(0).beanCreation.beanClass.getName()).isEqualTo(Bean038_2.class.getName());
    assertThat(grouped.get("bean_hello_3").get(0).beanCreation.beanClass.getName()).isEqualTo(Bean038_3.class.getName());
  }

  @Include(BeanConfig038.class)
  interface BeanContainer038_privateBeanGetter_strExactly extends BeanContainer {
    @SuppressWarnings("unused")
    BeanRef038_privateBeanGetter_strExactly ref();
  }

  @Test
  public void privateBeanGetter_strExactly() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer038_privateBeanGetter_strExactly.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    bcm.usingBeanReferences.forEach(ref -> {
      System.out.println("sc = " + ref.sourceClass);
      System.out.println("place = " + ref.place.display());
      System.out.println();
    });

    BeanReference target = bcm.usingBeanReferences.stream()
        .filter(ref -> ref.sourceClass == Bean038.class)
        .findAny()
        .orElseThrow(TestUtil.ElementNotFound::new);

    assertThat(target.getterCreations).hasSize(1);

    assertThat(target.getterCreations.get(0).beanCreation.beanId()).isEqualTo("bean_hello_.*");
  }
}

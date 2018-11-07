package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.core.Qualifier;
import kz.greetgo.depinject.gen.t03x.test_beans036.beans4.BeanFactory036_4;
import kz.greetgo.depinject.gen.t03x.test_beans036.beans4.BeanRef036_4;
import kz.greetgo.depinject.gen.t03x.test_beans036.beans5.BeanFactory036_5;
import kz.greetgo.depinject.gen.t03x.test_beans036.beans5.BeanRef036_5;
import kz.greetgo.depinject.gen.t03x.test_beans036.root.BeanConfig036;
import kz.greetgo.depinject.gen.t03x.test_beans036.root.BeanRef036_1;
import kz.greetgo.depinject.gen.t03x.test_beans036.root.BeanRef036_2;
import kz.greetgo.depinject.gen.t03x.test_beans036.root.BeanRef036_3;
import kz.greetgo.depinject.gen.t03x.test_beans036.root.BeanTarget036_1;
import kz.greetgo.depinject.gen.t03x.test_beans036.root.BeanTarget036_2;
import kz.greetgo.depinject.gen.t03x.test_beans036.root.BeanTarget036_3;
import kz.greetgo.depinject.gen.t03x.test_beans036.root.BeanTarget036_6;
import kz.greetgo.depinject.gen.t03x.test_beans039.BeanConfig039;
import kz.greetgo.depinject.gen.t03x.test_beans039.MainBean039;
import kz.greetgo.depinject.gen.t04x.test_beans040.BeanConfig040;
import kz.greetgo.depinject.gen.t04x.test_beans040.MainBean040;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.fest.assertions.api.Assertions.assertThat;

public class BeanContainerManagerReadQualifierTest {

  @Include(BeanConfig036.class)
  interface BeanContainer036 extends BeanContainer {
    @SuppressWarnings("unused")
    BeanRef036_1 ref1();

    @SuppressWarnings("unused")
    BeanRef036_2 ref2();

    @SuppressWarnings("unused")
    BeanRef036_3 ref3();

    @SuppressWarnings("unused")
    BeanRef036_4 ref4();
  }

  @Test
  public void read_qualifier_InPublicBeanGetter() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer036.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    BeanReference target = bcm.usingBeanReferences.stream()
        .filter(ref -> ref.sourceClass == BeanTarget036_1.class)
        .findAny()
        .orElseThrow(TestUtil.ElementNotFound::new);

    assertThat(target.place.type()).isEqualTo(BeanReference.PlaceType.InPublicBeanGetter);

    assertThat(target.place.qualifier().value()).isEqualTo("public_bean_getter_qualifier_0187");

//    bcm.usingBeanReferences.forEach(ref -> {
//      System.out.println("sc = " + ref.sourceClass);
//      System.out.println("place = " + ref.place.display());
//    });

  }

  @Test
  public void read_qualifier_InConstructorArg_annotation_in_arg() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer036.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

//    bcm.usingBeanReferences.forEach(ref -> {
//      System.out.println("sc = " + ref.sourceClass);
//      System.out.println("place = " + ref.place.display());
//    });

    BeanReference target = bcm.usingBeanReferences.stream()
        .filter(ref -> ref.sourceClass == BeanTarget036_2.class)
        .findAny()
        .orElseThrow(TestUtil.ElementNotFound::new);

    assertThat(target.place.type()).isEqualTo(BeanReference.PlaceType.InConstructorArg);

    assertThat(target.place.qualifier().value()).isEqualTo("in_constructor_arg_1672748");
  }

  @Test
  public void read_qualifier_InConstructorArg_annotation_in_private_field() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer036.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

//    bcm.usingBeanReferences.forEach(ref -> {
//      System.out.println("sc = " + ref.sourceClass);
//      System.out.println("place = " + ref.place.display());
//    });

    BeanReference target = bcm.usingBeanReferences.stream()
        .filter(ref -> ref.sourceClass == BeanTarget036_3.class)
        .findAny()
        .orElseThrow(TestUtil.ElementNotFound::new);

    assertThat(target.place.type()).isEqualTo(BeanReference.PlaceType.InConstructorArg);

    assertThat(target.place.qualifier().value()).isEqualTo("in_constructor_arg_73746t356");
  }

  @Include(BeanConfig036.class)
  interface BeanContainer036_4 extends BeanContainer {
    @SuppressWarnings("unused")
    BeanRef036_4 ref4();
  }

  @Test
  public void read_qualifier_InBeanFactory() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer036_4.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

//    bcm.usingBeanReferences.forEach(ref -> {
//      System.out.println("ref     = " + ref);
//      System.out.println("  sc    = " + ref.sourceClass);
//      System.out.println("  place = " + ref.place.type() + " : " + ref.place.display());
//    });
//    System.out.println();

    BeanReference target = bcm.usingBeanReferences.stream()
        .filter(ref -> ref.sourceClass == BeanFactory036_4.class)
        .findAny()
        .orElseThrow(TestUtil.ElementNotFound::new);

    assertThat(target.place.type()).isEqualTo(BeanReference.PlaceType.InBeanFactory);

    assertThat(target.place.qualifier().value()).isEqualTo("read_qualifier_InBeanFactory_928375");
  }

  @Include(BeanConfig036.class)
  interface BeanContainer036_5 extends BeanContainer {
    @SuppressWarnings("unused")
    BeanRef036_5 ref5();
  }

  @Test
  public void read_qualifier_InAnnotationFactoredBy() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer036_5.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

//    bcm.usingBeanReferences.forEach(ref -> {
//      System.out.println("ref     = " + ref);
//      System.out.println("  sc    = " + ref.sourceClass);
//      System.out.println("  place = " + ref.place.type() + " : " + ref.place.display());
//    });
//    System.out.println();

    BeanReference target = bcm.usingBeanReferences.stream()
        .filter(ref -> ref.sourceClass == BeanFactory036_5.class)
        .findAny()
        .orElseThrow(TestUtil.ElementNotFound::new);

    assertThat(target.place.type()).isEqualTo(BeanReference.PlaceType.InAnnotationFactoredBy);

    assertThat(target.place.qualifier().value()).isEqualTo("read_qualifier_InAnnotationFactoredBy_3245354");
  }

  @Include(BeanConfig036.class)
  interface BeanContainer036_6 extends BeanContainer {
    @SuppressWarnings("unused")
    @Qualifier("read_qualifier_InBeanContainerMethod_326453")
    BeanTarget036_6 ref6();
  }

  @Test
  public void read_qualifier_InBeanContainerMethod() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer036_6.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

//    bcm.usingBeanReferences.forEach(ref -> {
//      System.out.println("ref     = " + ref);
//      System.out.println("  sc    = " + ref.sourceClass);
//      System.out.println("  place = " + ref.place.type() + " : " + ref.place.display());
//    });
//    System.out.println();

    BeanReference target = bcm.usingBeanReferences.stream()
        .filter(ref -> ref.sourceClass == BeanTarget036_6.class)
        .findAny()
        .orElseThrow(TestUtil.ElementNotFound::new);

    assertThat(target.place.type()).isEqualTo(BeanReference.PlaceType.InBeanContainerMethod);

    assertThat(target.place.qualifier().value()).isEqualTo("read_qualifier_InBeanContainerMethod_326453");
  }

  @Include(BeanConfig039.class)
  interface BeanContainer039 extends BeanContainer {
    @SuppressWarnings("unused")
    MainBean039 mainBean();
  }

  @Test
  public void read_qualifier_of_private_bean_getter_with_same_types() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer039.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

//    bcm.usingBeanReferences.forEach(ref ->
//        System.out.println("place = " + ref.place.type() + " : " + ref.place.display()));
//    System.out.println();

    Set<String> qualifierSet = bcm.usingBeanReferences
        .stream()
        .map(r -> r.place)
        .map(BeanReference.Place::qualifier)
        .map(Qualifier::value)
        .filter(value -> value.length() > 0)
        .collect(Collectors.toSet());

    assertThat(qualifierSet).containsOnly("bean1", "bean2", "bean3");
  }

  @Include(BeanConfig040.class)
  interface BeanContainer040 extends BeanContainer {
    @SuppressWarnings("unused")
    MainBean040 mainBean();
  }

  @Test
  public void read_qualifier_of_private_bean_getter_with_same_types_from_parent_too() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer040.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

//    bcm.usingBeanReferences.forEach(ref ->
//        System.out.println("place = " + ref.place.type() + " : " + ref.place.display()));
//    System.out.println();

    Set<String> qualifierSet = bcm.usingBeanReferences
        .stream()
        .map(r -> r.place)
        .map(BeanReference.Place::qualifier)
        .map(Qualifier::value)
        .filter(value -> value.length() > 0)
        .collect(Collectors.toSet());

    assertThat(qualifierSet).containsOnly("bean1", "bean2", "bean3");
  }
}

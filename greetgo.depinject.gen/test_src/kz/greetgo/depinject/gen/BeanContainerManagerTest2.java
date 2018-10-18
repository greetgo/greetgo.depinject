package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.errors.NoMethodsInBeanContainer;
import kz.greetgo.depinject.gen.test_beans010.BeanA1;
import kz.greetgo.depinject.gen.test_beans010.BeanA1_impl;
import kz.greetgo.depinject.gen.test_beans010.BeanA2;
import kz.greetgo.depinject.gen.test_beans010.BeanA3;
import kz.greetgo.depinject.gen.test_beans010.BeanBSimple;
import kz.greetgo.depinject.gen.test_beans010.BeanConfig010;
import kz.greetgo.depinject.gen.test_beans010.ZGetters;
import kz.greetgo.depinject.gen.test_beans018.Bean018_ann;
import kz.greetgo.depinject.gen.test_beans018.Bean018_both;
import kz.greetgo.depinject.gen.test_beans018.Bean018_empty;
import kz.greetgo.depinject.gen.test_beans018.Bean018_iface;
import kz.greetgo.depinject.gen.test_beans018.BeanConfig018;
import kz.greetgo.depinject.gen.test_beans027.container.BeanContainerForTestingUtil;
import kz.greetgo.depinject.gen.test_beans033.BeanConfig033;
import kz.greetgo.depinject.gen.test_beans033.MainBean033;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class BeanContainerManagerTest2 {

  @Include(BeanConfig010.class)
  interface NoMethodsInBeanContainerWOW extends BeanContainer {}

  @Test(expectedExceptions = NoMethodsInBeanContainer.class)
  public void writeBeanContainerMethods_NoMethodsInBeanContainer() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(NoMethodsInBeanContainerWOW.class);
    bcm.prepareToWrite();

    //
    //
    bcm.writeBeanContainerMethods(0, null);
    //
    //
  }

  @SuppressWarnings("unused")
  @Include(BeanConfig010.class)
  public interface BeanContainer010 extends BeanContainer {
    ZGetters getZ_Getters();

    List<BeanA1> take_beanA1();

    List<BeanA1_impl> take_beanA1_impl_list();

    BeanA1_impl take_beanA1_impl();

    List<BeanA2> take_beanA2();

    List<BeanA3> take_beanA3();

    BeanBSimple getBeanBSimple();
  }

  @Test
  public void writeBeanContainerMethods() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer010.class);
    bcm.prepareToWrite();

    OuterToPrintStream outer = new OuterToPrintStream("  ", System.out);

    //
    //
    bcm.writeBeanContainerMethods(1, outer);
    //
    //

  }

  @Test
  public void writeBeanCreation() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer010.class);
    bcm.prepareToWrite();

    Outer outer = new OuterToPrintStream("  ", System.out);

    //
    //
    bcm.writeBeanCreations(1, outer);
    //
    //

  }

  @Test
  public void writeBeanContainerImpl_010() {
    generate(BeanContainer010.class);
  }

  private void generate(Class<?> bci) {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(bci);
    bcm.prepareToWrite();

    String packageName = bci.getPackage().getName();

    String classSimpleName = bci.getSimpleName() + "_IMPL";

    File javaFile = new File(TestUtil.buildDir() + "/gen_src/" + packageName.replaceAll("\\.", "/")
        + '/' + classSimpleName + ".java");

    javaFile.getParentFile().mkdirs();

    try (Outer outer = OuterTo.file(javaFile)) {

      //
      //
      bcm.writeBeanContainerImpl0(outer, packageName, classSimpleName);
      //
      //

    }
  }

  @Test
  public void prepareToWrite_BeanContainerForTestingUtil() {
    Class<?> bci = BeanContainerForTestingUtil.class;

    Context context = new Context();
    BeanContainerManager bcm = context.createManager(bci);
    bcm.prepareToWrite();

  }

  @SuppressWarnings("unused")
  @Include(BeanConfig018.class)
  interface BeanContainer018 extends BeanContainer {
    Bean018_ann bean018_ann();

    Bean018_both bean018_both();

    Bean018_empty bean018_empty();

    Bean018_iface bean018_iface();
  }

  @Test
  public void prepareToWrite_018_replacer() {
    generate(BeanContainer018.class);
  }

  @Include(BeanConfig033.class)
  interface BeanContainer033 extends BeanContainer {
    @SuppressWarnings("unused")
    MainBean033 mainBean();
  }

  @Test
  public void prepareToWrite_033_constructorOfBeanWithArguments() {
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(BeanContainer033.class);

    //
    //
    bcm.prepareToWrite();
    //
    //

    System.out.println(" *** *** START TEST prepareToWrite_033_constructorOfBeanWithArguments");

    System.out.println("---> BEGIN Preparations:");
    bcm.preparations.forEach(System.out::println);
    System.out.println("---> END   Preparations:\n");

    Collections.reverse(bcm.beanCreationList);
    System.out.println("---> BEGIN BeanCreationList");
    bcm.beanCreationList.forEach(System.out::println);
    System.out.println("---> END   BeanCreationList");

    System.out.println();

    for (BeanCreation beanCreation : bcm.beanCreationList) {
      System.out.println("=== beanCreation = " + beanCreation);
      System.out.println();
      for (BeanGetterInPublicField beanGetterInPublicField : beanCreation.beanGetterInPublicFieldList) {
        System.out.println(beanGetterInPublicField);
      }
      System.out.println();
    }

    System.out.println(" *** *** END TEST prepareToWrite_033_constructorOfBeanWithArguments");
  }
}

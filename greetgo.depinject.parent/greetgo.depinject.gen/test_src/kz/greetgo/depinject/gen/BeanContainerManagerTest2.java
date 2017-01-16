package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.errors.NoMethodsInBeanContainer;
import kz.greetgo.depinject.gen.test_beans027.container.BeanContainerForTestingUtil;
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
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

public class BeanContainerManagerTest2 {

  @Include(BeanConfig010.class)
  interface NoMethodsInBeanContainerWOW extends BeanContainer {
  }

  @Test(expectedExceptions = NoMethodsInBeanContainer.class)
  public void writeBeanContainerMethods_NoMethodsInBeanContainer() throws Exception {
    BeanContainerManager bcm = new BeanContainerManager(NoMethodsInBeanContainerWOW.class);
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
  public void writeBeanContainerMethods() throws Exception {
    BeanContainerManager bcm = new BeanContainerManager(BeanContainer010.class);
    bcm.prepareToWrite();

    OuterToPrintStream outer = new OuterToPrintStream("  ", System.out);

    //
    //
    bcm.writeBeanContainerMethods(1, outer);
    //
    //

  }

  @Test
  public void writeBeanCreation() throws Exception {
    BeanContainerManager bcm = new BeanContainerManager(BeanContainer010.class);
    bcm.prepareToWrite();

    Outer outer = new OuterToPrintStream("  ", System.out);

    //
    //
    bcm.writeBeanCreations(1, outer);
    //
    //

  }

  @Test
  public void writeBeanContainerImpl_010() throws Exception {
    generate(BeanContainer010.class);
  }

  private void generate(Class<?> bci) {
    BeanContainerManager bcm = new BeanContainerManager(bci);
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
  public void prepareToWrite_BeanContainerForTestingUtil() throws Exception {
    Class<?> bci = BeanContainerForTestingUtil.class;

    BeanContainerManager bcm = new BeanContainerManager(bci);
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
  public void prepareToWrite_018_replacer() throws Exception {
    generate(BeanContainer018.class);
  }

}
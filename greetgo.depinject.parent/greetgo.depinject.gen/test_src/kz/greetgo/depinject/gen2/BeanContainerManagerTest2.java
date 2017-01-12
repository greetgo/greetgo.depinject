package kz.greetgo.depinject.gen2;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.errors.NoMethodsInBeanContainer;
import kz.greetgo.depinject.gen2.test_beans010.BeanA1;
import kz.greetgo.depinject.gen2.test_beans010.BeanA1_impl;
import kz.greetgo.depinject.gen2.test_beans010.BeanA2;
import kz.greetgo.depinject.gen2.test_beans010.BeanA3;
import kz.greetgo.depinject.gen2.test_beans010.BeanBSimple;
import kz.greetgo.depinject.gen2.test_beans010.BeanConfig010;
import kz.greetgo.depinject.gen2.test_beans010.ZGetters;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static kz.greetgo.depinject.gen2.TestUtil.buildDir;

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

    OuterToPrintStream outer = new OuterToPrintStream("  ", System.out);

    //
    //
    bcm.writeBeanCreations(1, outer);
    //
    //

  }

  @Test
  public void writeBeanContainerImpl() throws Exception {
    Class<?> bci = BeanContainer010.class;

    BeanContainerManager bcm = new BeanContainerManager(bci);
    bcm.prepareToWrite();

    String packageName = bci.getPackage().getName();
    String classSimpleName = bci.getSimpleName() + "_IMPLEMENT";

    File javaFile = new File(buildDir() + "/gen_src/" + packageName.replaceAll("\\.", "/")
      + '/' + classSimpleName + ".java");

    javaFile.getParentFile().mkdirs();

    try (Outer outer = OuterTo.file(javaFile)) {

      //
      //
      bcm.writeBeanContainerImpl(outer, packageName, classSimpleName);
      //
      //

    }

  }
}
package kz.greetgo.depinject.gwt.gen;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.gen.errors.NoBeanContainer;
import kz.greetgo.depinject.gen.errors.NoInclude;
import kz.greetgo.depinject.gwt.gen.example.TestBeanContainer;
import kz.greetgo.depinject.gwt.gen.example.TestBeanContainerNoInclude;
import kz.greetgo.depinject.gwt.gen.example.beans1.AsdBeanImpl;
import kz.greetgo.depinject.gwt.gen.example.beans2.place1.asd.Place1bean;
import kz.greetgo.depinject.gwt.gen.example.beans2.place2.asd.Place2bean;
import org.testng.annotations.Test;

import java.io.File;

import static org.fest.assertions.api.Assertions.assertThat;

public class BeanContainerImplGeneratorTest {

  @Test(expectedExceptions = NoInterface.class)
  public void findBeanClassSet_notIface() throws Exception {
    BeanContainerImplGenerator x = new BeanContainerImplGenerator();
    x.beanContainerIface = Integer.class;

    x.findBeanClassSet();
  }

  @Test(expectedExceptions = NoBeanContainer.class)
  public void findBeanClassSet_noBeanContainer() throws Exception {
    BeanContainerImplGenerator x = new BeanContainerImplGenerator();
    x.beanContainerIface = Bean.class;

    x.findBeanClassSet();
  }

  @Test(expectedExceptions = NoInclude.class)
  public void findBeanClassSet_noInclude() throws Exception {
    BeanContainerImplGenerator x = new BeanContainerImplGenerator();
    x.beanContainerIface = TestBeanContainerNoInclude.class;

    x.findBeanClassSet();
  }

  @Test
  public void findBeanClassSet_ok() throws Exception {
    BeanContainerImplGenerator x = new BeanContainerImplGenerator();
    x.beanContainerIface = TestBeanContainer.class;

    x.findBeanClassSet();

    assertThat(x.beanClassSet).contains(AsdBeanImpl.class, Place1bean.class, Place2bean.class);
  }

  @Test
  public void generate() throws Exception {

    BeanContainerImplGenerator x = new BeanContainerImplGenerator();
    x.beanContainerIface = TestBeanContainer.class;

    String pre = "";

    {
      String ideaDir = "greetgo.depinject.gwt/";
      if (new File(ideaDir).isDirectory()) {
        pre = ideaDir;
      }
    }

    x.srcDir = pre + "build/generated_source";
    x.packageName = "kz.greetgo.depinject.gen.asd";
    x.implClassName = "HelloImpl";

    x.generate();

    assertThat(1);
  }

}

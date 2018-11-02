package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.t02x.test_beans027.beans.BeanConfig027;
import kz.greetgo.depinject.gen.t02x.test_beans027.interfaces.IBeanB2;

import java.io.File;

public class BeanContainerGeneratorProbe {
  @SuppressWarnings("unused")
  @Include({BeanConfig027.class})
  private interface TestBeanContainer extends BeanContainer {
    IBeanB2 getIBeanB2();
  }

  public static void main(String[] args) {
    BeanContainerGenerator g = new BeanContainerGenerator();
    g.beanContainerInterface = TestBeanContainer.class;

    g.implClassName = "TestBeanContainerImpl";
    g.packageName = "kz.greetgo.depinject.gen.test";

    String pre = "";
    {
      String prj = "greetgo.depinject.gen/";
      if (new File(prj).isDirectory()) {
        pre = prj;
      }
    }

    g.writeToSourceDir(pre + "build/generated_source");

    System.out.println("BeanContainerGeneratorProbe COMPLETE");
  }
}

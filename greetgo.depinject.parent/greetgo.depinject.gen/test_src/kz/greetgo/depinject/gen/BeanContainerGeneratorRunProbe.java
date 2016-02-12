package kz.greetgo.depinject.gen;

import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.beans.MainConfig;
import kz.greetgo.depinject.gen.interfaces.IBeanB2;
import kz.greetgo.util.ServerUtil;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;

public class BeanContainerGeneratorRunProbe {
  @Include({MainConfig.class})
  public interface TestBeanContainer extends BeanContainer {
    IBeanB2 getIBeanB2();
  }

  public static void main(String[] args) throws Exception {
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

    String src = pre + "build/run_generated_source";

    g.writeToSourceDir(src);

    System.out.println("java.class.path = " + System.getProperty("java.class.path"));

    final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    final int exitCode = compiler.run(System.in, System.out, System.err,
      src + "/kz/greetgo/depinject/gen/test/TestBeanContainerImpl.java");

    System.out.println("exitCode = " + exitCode);

    ServerUtil.addToClasspath(new File(src));

    final Class<?> containerClass = Class.forName("kz.greetgo.depinject.gen.test.TestBeanContainerImpl");
    System.out.println("containerClass = " + containerClass);

    TestBeanContainer container = (TestBeanContainer) containerClass.newInstance();

    System.out.println("container = " + container);

    container.getIBeanB2().goodDay();

    System.out.println("BeanContainerGeneratorRunProbe COMPLETE");
  }
}

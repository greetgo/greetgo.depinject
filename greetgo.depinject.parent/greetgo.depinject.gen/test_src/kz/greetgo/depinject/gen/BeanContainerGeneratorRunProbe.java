package kz.greetgo.depinject.gen;

import com.sun.xml.internal.ws.developer.JAXBContextFactory;
import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.beans.MainConfig;
import kz.greetgo.depinject.gen.interfaces.IBeanB2;
import kz.greetgo.java_compiler.JavaCompiler;
import kz.greetgo.java_compiler.JavaCompilerFactory;
import kz.greetgo.util.ServerUtil;

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

    final JavaCompiler compiler = JavaCompilerFactory.createDefault();

    compiler.compile(src + "/kz/greetgo/depinject/gen/test/TestBeanContainerImpl.java");

    ServerUtil.addToClasspath(src);

    final Class<?> containerClass = Class.forName("kz.greetgo.depinject.gen.test.TestBeanContainerImpl");
    System.out.println("containerClass = " + containerClass);

    TestBeanContainer container = (TestBeanContainer) containerClass.newInstance();

    System.out.println("container = " + container);

    container.getIBeanB2().goodDay();

    System.out.println("BeanContainerGeneratorRunProbe COMPLETE");
  }
}

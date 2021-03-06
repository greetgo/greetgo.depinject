package kz.greetgo.depinject.gen;

import java.io.File;
import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.t02x.test_beans027.beans.BeanConfig027;
import kz.greetgo.depinject.gen.t02x.test_beans027.interfaces.IBeanB2;
import kz.greetgo.java_compiler.FilesClassLoader;
import kz.greetgo.java_compiler.JavaCompiler;
import kz.greetgo.java_compiler.JavaCompilerFactory;

public class BeanContainerGeneratorRunProbe {
  @Include({BeanConfig027.class})
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

    try (FilesClassLoader classLoader = new FilesClassLoader(ClassLoader.getSystemClassLoader())) {
      classLoader.addClasspath(new File(src));

      final Class<?> containerClass = classLoader.loadClass("kz.greetgo.depinject.gen.test.TestBeanContainerImpl");
      System.out.println("containerClass = " + containerClass);

      TestBeanContainer container = (TestBeanContainer) containerClass.newInstance();

      System.out.println("container = " + container);

      container.getIBeanB2().goodDay();

      System.out.println("BeanContainerGeneratorRunProbe COMPLETE");
    }
  }
}

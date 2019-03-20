package kz.greetgo.depinject.testng;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.BeanScanner;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.depinject.gen.BeanContainerGenerator;
import kz.greetgo.depinject.gen.DepinjectUtil;
import kz.greetgo.java_compiler.FilesClassLoader;
import kz.greetgo.java_compiler.JavaCompiler;
import kz.greetgo.java_compiler.JavaCompilerFactory;
import kz.greetgo.util.RND;

import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static kz.greetgo.util.ServerUtil.dummyCheck;
import static kz.greetgo.util.ServerUtil.extractName;
import static kz.greetgo.util.ServerUtil.extractPackage;
import static kz.greetgo.util.ServerUtil.resolveFile;

public class DepinjectTestNg {

  public static String prepareDepinjectTestNg(AbstractDepinjectTestNg testNgTest, String srcTempDir) throws Exception {
    final JavaCompiler compiler = JavaCompilerFactory.createDefault();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");

    String srcDir = srcTempDir + "/" + sdf.format(new Date()) + "-" + RND.strInt(10);

    List<Class<?>> classList = new ArrayList<>();

    final Class<?> testNgTestClass = testNgTest.getClass();
    appendAllIncludes(classList, testNgTestClass);

    if (classList.isEmpty()) {
      return srcDir;
    }

    String outerPackage = "a" + RND.strInt(10) + ".";

    String containerInterface
      = outerPackage + testNgTestClass.getSimpleName() + "_ContainerInterface_" + RND.strInt(10);
    String containerInterfaceImpl
      = outerPackage + testNgTestClass.getSimpleName() + "_ContainerInterfaceImpl_" + RND.strInt(10);

    String testNgTestStaticFactory
      = outerPackage + testNgTestClass.getSimpleName() + "_StaticFactory_" + RND.strInt(10);
    String testNgTestConfig
      = outerPackage + testNgTestClass.getSimpleName() + "_Config_" + RND.strInt(10);

    final File testNgTestConfigJava = writeTestNgTestConfig(srcDir, testNgTestConfig);

    final File testNgTestStaticFactoryJava = writeTestNgStaticFactory(srcDir, testNgTestStaticFactory, testNgTestClass);

    final File containerInterfaceJava = writeTestNgTestConfig(srcDir, containerInterface,
      testNgTestClass, testNgTestConfig, classList);

    FilesClassLoader classLoader = DepinjectUtil.ensureAndGetDepinjectLoader();
    classLoader.addFile(new File(srcDir));

    compiler.classpath().add(new File(srcDir));

    compiler.compile(testNgTestConfigJava);

    compiler.compile(containerInterfaceJava);

    compiler.compile(testNgTestStaticFactoryJava);

    {
      BeanContainerGenerator g = new BeanContainerGenerator();
      g.beanContainerInterface = classLoader.loadClass(containerInterface);

      g.implClassName = extractName(containerInterfaceImpl);
      g.packageName = extractPackage(containerInterfaceImpl);

      g.writeToSourceDir(srcDir);
    }

    final File containerInterfaceImplJava = resolveFile(srcDir, containerInterfaceImpl, ".java");

    compiler.compile(containerInterfaceImplJava);

    final Class<?> containerInterfaceImplClass = classLoader.loadClass(containerInterfaceImpl);

    final Class<?> testNgTestStaticFactoryClass = classLoader.loadClass(testNgTestStaticFactory);
    testNgTestStaticFactoryClass.getField("instance").set(null, testNgTest);

    final Object containerInstance = containerInterfaceImplClass.newInstance();

    containerInterfaceImplClass.getMethod("getMain").invoke(containerInstance);

    return srcDir;
  }

  private static File writeTestNgTestConfig(String srcDir, String containerInterface, Class<?> testNgTestClass,
                                            String testNgTestConfig, List<Class<?>> classList) throws Exception {

    final File file = resolveFile(srcDir, containerInterface, ".java");
    dummyCheck(file.getParentFile().mkdirs());

    try (final PrintStream out = new PrintStream(file, "UTF-8")) {
      final String className = extractName(containerInterface);
      final String packageName = extractPackage(containerInterface);

      if (packageName != null) {
        out.println("package " + packageName + ";");
      }

      out.println();

      out.println('@' + Include.class.getName() + "({" + testNgTestConfig + ".class");
      for (Class<?> aClass : classList) {
        out.println("  , " + aClass.getName() + ".class");
      }
      out.println("})");
      out.println("public interface " + className + " extends " + BeanContainer.class.getName() + " {");
      out.println("  " + testNgTestClass.getName() + " getMain();");
      out.println('}');
    }

    return file;
  }

  private static File writeTestNgStaticFactory(String srcDir, String testNgTestStaticFactory, Class<?> testNgTestClass)
    throws Exception {

    final File file = resolveFile(srcDir, testNgTestStaticFactory, ".java");
    dummyCheck(file.getParentFile().mkdirs());

    try (final PrintStream out = new PrintStream(file, "UTF-8")) {

      final String className = extractName(testNgTestStaticFactory);
      final String packageName = extractPackage(testNgTestStaticFactory);

      if (packageName != null) {
        out.println("package " + packageName + ";");
      }

      out.println();

      out.println("@" + Bean.class.getName());
      out.println("public class " + className + " {");
      out.println("  ");
      out.println("  public static " + testNgTestClass.getName() + " instance = null;");
      out.println("  ");
      out.println("  @" + Bean.class.getName());
      out.println("  public " + testNgTestClass.getName() + " getInstance() {return instance;}");
      out.println("  ");
      out.println("}");
    }

    return file;
  }

  private static File writeTestNgTestConfig(String srcDir, String testNgTestConfig) throws Exception {
    final File file = resolveFile(srcDir, testNgTestConfig, ".java");
    dummyCheck(file.getParentFile().mkdirs());

    try (final PrintStream out = new PrintStream(file, "UTF-8")) {

      final String className = extractName(testNgTestConfig);
      final String packageName = extractPackage(testNgTestConfig);

      if (packageName != null) {
        out.println("package " + packageName + ";");
      }

      out.println();

      out.println("@" + BeanConfig.class.getName());
      out.println("@" + BeanScanner.class.getName());
      out.println("public class " + className + " {}");
    }

    return file;
  }


  private static void appendAllIncludes(List<Class<?>> classList, Class<?> aClass) {
    if (aClass == null) {
      return;
    }
    if (aClass == Object.class) {
      return;
    }

    final ContainerConfig a = aClass.getAnnotation(ContainerConfig.class);

    if (a == null) {
      appendAllIncludes(classList, aClass.getSuperclass());
      return;
    }

    Collections.addAll(classList, a.value());

    if (a.inherit()) {
      appendAllIncludes(classList, aClass.getSuperclass());
    }
  }
}

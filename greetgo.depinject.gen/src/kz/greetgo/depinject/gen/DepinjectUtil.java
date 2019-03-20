package kz.greetgo.depinject.gen;

import kz.greetgo.class_scanner.ClassScanner;
import kz.greetgo.class_scanner.ClassScannerDef;
import kz.greetgo.depinject.Depinject;
import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.java_compiler.FilesClassLoader;
import kz.greetgo.java_compiler.JavaCompiler;
import kz.greetgo.java_compiler.JavaCompilerFactory;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DepinjectUtil {
  public static Class<?> typeToClass(Type type) {
    if (type instanceof Class) {
      return (Class<?>) type;
    }
    if (type instanceof ParameterizedType) {
      ParameterizedType pt = (ParameterizedType) type;
      return (Class<?>) pt.getRawType();
    }
    return null;
  }

  public static String toCode(Type type) {
    return toCode0(type).replaceAll("\\$", ".");
  }

  private static String toCode0(Type type) {
    if (type instanceof Class) {
      return ((Class<?>) type).getName();
    }
    return type.toString();
  }

  @SuppressWarnings("unused")
  public static String spaces(int spaces) {
    char[] s = new char[spaces];
    for (int i = 0, n = s.length; i < n; i++) {
      s[i] = ' ';
    }
    return new String(s);
  }

  public static List<JavaFile> generateBeanContainersSources(String packageName, String srcDir) {
    List<JavaFile> filesToCompile = new ArrayList<>();

    {
      ClassScanner classScanner = new ClassScannerDef();
      for (Class<?> aClass : classScanner.scanPackage(packageName)) {
        if (!BeanContainer.class.isAssignableFrom(aClass)) {
          continue;
        }
        if (aClass.getAnnotation(Include.class) == null) {
          continue;
        }


        BeanContainerGenerator bcg = new BeanContainerGenerator();
        bcg.beanContainerInterface = aClass;
        bcg.implClassName = aClass.getSimpleName() + BeanContainer.IMPL_POSTFIX;
        bcg.packageName = aClass.getPackage().getName();

        File file = bcg.writeToSourceDir(srcDir);

        filesToCompile.add(new JavaFile(file, srcDir, bcg.packageName, bcg.implClassName));
      }
    }

    return filesToCompile;
  }

  public static List<JavaFile> implementBeanContainers(String packageName, String srcDir) {
    List<JavaFile> filesToCompile = generateBeanContainersSources(packageName, srcDir);

    final JavaCompiler compiler = JavaCompilerFactory.createDefault();

    for (JavaFile javaFile : filesToCompile) {
      compiler.compile(javaFile.file);
    }

    return filesToCompile;
  }

  public static FilesClassLoader ensureAndGetDepinjectLoader() {

    ClassLoader classLoader = Depinject.additionalLoader
      .updateAndGet(loader -> loader != null ? loader : new FilesClassLoader(ClassLoader.getSystemClassLoader()));

    if (!(classLoader instanceof FilesClassLoader)) {
      throw new IllegalStateException("Illegal depinject class loader class: " + classLoader.getClass()
        + ". Must be " + FilesClassLoader.class);
    }

    return (FilesClassLoader) classLoader;

  }

  public static void implementAndUseBeanContainers(String packageName, String srcDir) {

    FilesClassLoader classLoader = ensureAndGetDepinjectLoader();

    for (JavaFile javaFile : implementBeanContainers(packageName, srcDir)) {

      classLoader.addClasspath(javaFile.srcDirFile());

    }

  }

  public static DepinjectVersion version() {
    String version = Utils.streamToStr(DepinjectUtil.class.getResourceAsStream("/depinject_version.txt")).trim();
    return DepinjectVersion.parse(version);
  }
}

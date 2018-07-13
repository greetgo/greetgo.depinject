package kz.greetgo.depinject.gen;

import kz.greetgo.class_scanner.ClassScanner;
import kz.greetgo.class_scanner.ClassScannerDef;
import kz.greetgo.depinject.core.BeanContainer;
import kz.greetgo.depinject.core.Include;
import kz.greetgo.java_compiler.JavaCompiler;
import kz.greetgo.java_compiler.JavaCompilerFactory;
import kz.greetgo.util.ServerUtil;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DepinjectUtil {
  public static Class<?> typeToClass(Type type) {
    if (type instanceof Class) return (Class<?>) type;
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
    if (type instanceof Class) return ((Class<?>) type).getName();
    return type.toString();
  }

  public static String spaces(int spaces) {
    char s[] = new char[spaces];
    for (int i = 0, n = s.length; i < n; i++) {
      s[i] = ' ';
    }
    return new String(s);
  }

  public static List<File> generateBeanContainersSources(String packageName, String srcDir) {
    List<File> filesToCompile = new ArrayList<>();

    {
      ClassScanner classScanner = new ClassScannerDef();
      for (Class<?> aClass : classScanner.scanPackage(packageName)) {
        if (!BeanContainer.class.isAssignableFrom(aClass)) continue;
        if (aClass.getAnnotation(Include.class) == null) continue;

        BeanContainerGenerator bcg = new BeanContainerGenerator();
        bcg.beanContainerInterface = aClass;
        bcg.implClassName = aClass.getSimpleName() + BeanContainer.IMPL_POSTFIX;
        bcg.packageName = aClass.getPackage().getName();

        filesToCompile.add(bcg.writeToSourceDir(srcDir));
      }
    }

    return filesToCompile;
  }

  public static void implementBeanContainers(String packageName, String srcDir) {
    List<File> filesToCompile = generateBeanContainersSources(packageName, srcDir);

    final JavaCompiler compiler = JavaCompilerFactory.createDefault();

    for (File file : filesToCompile) {
      compiler.compile(file);
    }
  }

  public static void implementAndUseBeanContainers(String packageName, String srcDir) throws Exception {
    implementBeanContainers(packageName, srcDir);

    ServerUtil.addToClasspath(srcDir);
  }
}

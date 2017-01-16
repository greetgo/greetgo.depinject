package kz.greetgo.depinject.gen;

import java.io.File;
import java.io.PrintWriter;

public class BeanContainerGenerator {

  public Class<?> beanContainerInterface;
  public String implClassName;
  public String packageName;

  public File writeToSourceDir(String sourceDir) {
    final String packagePath = packageName.replace('.', '/');
    File file = new File(sourceDir + '/' + packagePath + '/' + implClassName + ".java");
    file.getParentFile().mkdirs();

    try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
      writeTo(writer);
    } catch (Exception e) {
      if (e instanceof RuntimeException) throw (RuntimeException) e;
      throw new RuntimeException(e);
    }

    return file;
  }

  public void writeTo(PrintWriter writer) throws Exception {
    OuterToPrintWriter outer = new OuterToPrintWriter("  ", writer);
    BeanContainerManager bcm = new BeanContainerManager(beanContainerInterface);
    bcm.writeBeanContainerImpl(outer, packageName, implClassName);
  }
}

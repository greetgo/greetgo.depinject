package kz.greetgo.depinject.ap.engine;

import java.io.File;
import java.io.PrintWriter;

public class BeanContainerGenerator {

  public Class<?> beanContainerInterface;
  public String implClassName;
  public String packageName;

  public File writeToSourceDir(String sourceDir) {
    final String packagePath = packageName.replace('.', '/');
    File file = new File(sourceDir + '/' + packagePath + '/' + implClassName + ".java");
    //noinspection ResultOfMethodCallIgnored
    file.getParentFile().mkdirs();

    try (PrintWriter writer = new PrintWriter(file, "UTF-8")) {
      writeTo(writer);
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return file;
  }

  public void writeTo(PrintWriter writer) {
    OuterToPrintWriter outer = new OuterToPrintWriter("  ", writer);
    Context context = new Context();
    BeanContainerManager bcm = context.createManager(beanContainerInterface);
    bcm.writeBeanContainerImpl(outer, packageName, implClassName);
  }

}

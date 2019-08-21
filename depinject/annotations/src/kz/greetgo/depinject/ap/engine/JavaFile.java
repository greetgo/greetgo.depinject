package kz.greetgo.depinject.ap.engine;

import java.io.File;

public class JavaFile {
  public final File file;
  public final String srcDir;
  public final String packageName;
  public final String simpleName;

  public JavaFile(File file, String srcDir, String packageName, String simpleName) {
    this.file = file;
    this.srcDir = srcDir;
    this.packageName = packageName;
    this.simpleName = simpleName;
  }

  public String fullName() {
    if (packageName == null || packageName.length() == 0) {
      return simpleName;
    }
    return packageName + "." + simpleName;
  }

  public File srcDirFile() {
    return new File(srcDir);
  }

}

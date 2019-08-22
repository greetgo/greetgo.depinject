package kz.greetgo.depinject.ap.engine;

public class ClassName {
  public final String packageName;
  public final String name;

  public ClassName(String packageName, String name) {
    this.packageName = packageName;
    this.name = name;
  }

  public static ClassName parseQualifiedName(String qualifiedName) {
    int idx = qualifiedName.lastIndexOf('.');
    if (idx < 0) {
      return new ClassName(null, qualifiedName);
    }
    return new ClassName(qualifiedName.substring(0, idx), qualifiedName.substring(idx + 1));
  }

  public String qualifiedName() {
    if (packageName == null) {
      return name;
    }
    return packageName + '.' + name;
  }
}

package kz.greetgo.depinject.gradle

import java.util.stream.Collectors

class DepinjectPluginExt {

  final List<String> packageList = new ArrayList<>()

  @SuppressWarnings("GroovyUnusedDeclaration")
  void scanPackage(String packageName) {
    packageList.add(packageName)
  }

  void checkPackages() {
    if (packageList.isEmpty()) throw new RuntimeException(
      "Please, specify one or more packages to scan for BeanContainers. Do it in extension 'depinject' like:\n" +
        "  depinject {\n" +
        "    scanPackage 'com.example.package'\n" +
        "  }")
  }

  String packagesColon() {
    return packageList.stream().collect(Collectors.joining(":"))
  }

  String depinjectGeneratedSrc = "depinject_generated_src"
  String depinjectClasses = "depinject_classes"

  String depinjectGeneratedTestSrc = "depinject_generated_test_src"
  String depinjectTestClasses = "depinject_test_classes"

  String depinjectJarPostfix = "-depinject"
}

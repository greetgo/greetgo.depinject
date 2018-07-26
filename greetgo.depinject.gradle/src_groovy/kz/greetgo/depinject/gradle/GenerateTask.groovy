package kz.greetgo.depinject.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import static kz.greetgo.depinject.gen.DepinjectUtil.generateBeanContainersSources

class GenerateTask extends DefaultTask {

  String searchPackage;
  File outputDir;

  @TaskAction
  @SuppressWarnings("GroovyUnusedDeclaration")
  void generate() {
    generateBeanContainersSources(searchPackage, outputDir.getAbsolutePath())
  }

}
